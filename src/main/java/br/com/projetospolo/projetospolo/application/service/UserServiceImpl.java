package br.com.projetospolo.projetospolo.application.service;

import br.com.projetospolo.projetospolo.domain.dto.UserDTO;
import br.com.projetospolo.projetospolo.domain.filter.UserFilter;
import br.com.projetospolo.projetospolo.domain.form.UserForm;
import br.com.projetospolo.projetospolo.domain.form.UserUpdateForm;
import br.com.projetospolo.projetospolo.domain.mapper.UserMapper;
import br.com.projetospolo.projetospolo.domain.repository.RoleRepository;
import br.com.projetospolo.projetospolo.domain.repository.UserRepository;
import br.com.projetospolo.projetospolo.domain.type.RoleType;
import br.com.projetospolo.projetospolo.infrastructure.exception.BusinessException;
import br.com.projetospolo.projetospolo.infrastructure.service.ProjectService;
import br.com.projetospolo.projetospolo.infrastructure.service.RoleService;
import br.com.projetospolo.projetospolo.infrastructure.service.UserService;
import br.com.projetospolo.projetospolo.util.ExceptionDescriptionConstants;
import br.com.projetospolo.projetospolo.util.ExceptionMessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends UserService {

    private final UserAuthService authService;
    private final RoleService roleService;
    private final ProjectService projectService;

    private final UserRepository repository;
    private final RoleRepository roleRepository;

    private final UserMapper mapper;

    @Override
    public UserDTO create(UserForm userForm) {
        repository.findByEmail(userForm.getEmail())
            .ifPresent(
                saved -> {
                    throw BusinessException.builder()
                        .httpStatusCode(HttpStatus.BAD_REQUEST)
                        .message(ExceptionMessageConstants.EXEPTION_MESSAGE_CREDENTIALS_ALREADY_IN_USE)
                        .description(ExceptionDescriptionConstants.EXEPTION_DESCRIPTION_CREDENTIALS_ALREADY_IN_USE)
                        .build();
                }
            );

        var toSave = mapper.domainFromForm(userForm);
        toSave.setActive(true);
        toSave.setPassword(new BCryptPasswordEncoder().encode(toSave.getPassword()));
        toSave.setCreationDate(LocalDateTime.now());
        toSave.setRoles(
            Set.of(
                roleService.findById(
                    userForm.isIntern()
                        ? RoleType.INTERNO.getId()
                        : RoleType.EXTERNO.getId()
                )
            )
        );

        var saved = repository.save(toSave);

        return mapper.dtoFromDomain(saved);
    }

    @Override
    public UserDTO update(Long id, UserUpdateForm userForm) {
        var saved = repository.findById(id)
            .orElseThrow(
                () -> BusinessException.builder()
                    .httpStatusCode(HttpStatus.BAD_REQUEST)
                    .message(ExceptionMessageConstants.EXEPTION_MESSAGE_DATA_NOT_FOUND)
                    .description(ExceptionDescriptionConstants.EXEPTION_DESCRIPTION_USER_NOT_FOUND)
                    .build()
            );

        if (!saved.isActive()) {
            throw BusinessException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(ExceptionMessageConstants.EXEPTION_MESSAGE_DATA_NOT_FOUND)
                .description(ExceptionDescriptionConstants.EXEPTION_DESCRIPTION_USER_NOT_FOUND)
                .build();
        }

        var authenticatedUser = authService.getAuth();
        if (!Objects.equals(authenticatedUser.getUsername(), saved.getUsername())) {
            throw BusinessException.builder()
                .httpStatusCode(HttpStatus.UNAUTHORIZED)
                .message(ExceptionMessageConstants.EXEPTION_MESSAGE_UNAUTHORIZED_ACTION)
                .description(ExceptionDescriptionConstants.EXEPTION_DESCRIPTION_CANNOT_UPDATE_OTHER_USER)
                .build();
        }

        var updater = mapper.domainFromUpdateForm(userForm);
        if (
            (!Objects.equals(saved.getUsername(), updater.getUsername()) && repository.findByEmail(updater.getUsername()).isPresent())
        ) {
            throw BusinessException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(ExceptionMessageConstants.EXEPTION_MESSAGE_CREDENTIALS_ALREADY_IN_USE)
                .description(ExceptionDescriptionConstants.EXEPTION_DESCRIPTION_CREDENTIALS_ALREADY_IN_USE)
                .build();
        }

        if (Objects.isNull(userForm.getPassword()) || userForm.getPassword().isBlank()) {
            updater.setPassword(saved.getPassword());
        } else {
            var passwordLength = userForm.getPassword().length();
            if ((8 <= passwordLength) && (passwordLength <= 100)) {
                updater.setPassword(new BCryptPasswordEncoder().encode(userForm.getPassword()));
            } else {
                throw BusinessException.builder()
                    .httpStatusCode(HttpStatus.BAD_REQUEST)
                    .message(ExceptionMessageConstants.EXEPTION_MESSAGE_UNACCEPTACLE_DATA)
                    .description(ExceptionDescriptionConstants.EXEPTION_DESCRIPTION_PASSWORD_LENGTH)
                    .build();
            }
        }

        saved = mapper.transferInformation(updater, saved);

        saved.getRoles().clear();
        saved.getRoles().add(
            roleService.findById(
                userForm.isIntern()
                    ? RoleType.INTERNO.getId()
                    : RoleType.EXTERNO.getId()
            )
        );

        var updated = repository.save(saved);
        return mapper.dtoFromDomain(updated);
    }

    @Override
    public Page<UserDTO> read(UserFilter filter, Pageable pageable) {
        Long roleId = null;
        if (Objects.nonNull(filter.getIntern())) {
            roleId = RoleType.getRoleTypeByInternFlag(filter.getIntern()).getId();
        }
        return repository.filter(
            filter.getId(),
            filter.getFirstName(),
            filter.getLastName(),
            filter.getEmail(),
            roleId,
            pageable
        ).map(mapper::dtoFromDomain);
    }

    @Override
    public void delete(Long id) {
        var toDelete = repository.findById(id).orElseThrow(
            () -> BusinessException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(ExceptionMessageConstants.EXEPTION_MESSAGE_DATA_NOT_FOUND)
                .description(ExceptionDescriptionConstants.EXEPTION_DESCRIPTION_USER_NOT_FOUND)
                .build()
        );

        if (!toDelete.isActive()) {
            throw BusinessException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(ExceptionMessageConstants.EXEPTION_MESSAGE_DATA_NOT_FOUND)
                .description(ExceptionDescriptionConstants.EXEPTION_DESCRIPTION_USER_NOT_FOUND)
                .build();
        }

        toDelete.setActive(false);

        repository.save(toDelete);
    }

    @Override
    public Page<UserDTO> getAllUsersLeft(Long projectId, Pageable pageable) {

        var projectParticipants = projectService.findById(projectId).getParticipants();

        if (projectParticipants.isEmpty()) {
            return repository.findAll(pageable).map(mapper::dtoFromDomain);
        }
        var participantsIn = projectParticipants
            .stream()
            .map(UserDTO::getId)
            .collect(Collectors.toList());

        var usersLeft = repository.getUsersNotIn(participantsIn, pageable);

        return usersLeft.map(mapper::dtoFromDomain);
    }

    @Override
    public UserDTO findById(Long id) {
        var user = repository.findById(id).orElseThrow(
            () -> BusinessException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(ExceptionMessageConstants.EXEPTION_MESSAGE_DATA_NOT_FOUND)
                .description(ExceptionDescriptionConstants.EXEPTION_DESCRIPTION_USER_NOT_FOUND)
                .build()
        );

        if (!user.isActive()) {
            throw BusinessException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(ExceptionMessageConstants.EXEPTION_MESSAGE_DATA_NOT_FOUND)
                .description(ExceptionDescriptionConstants.EXEPTION_DESCRIPTION_USER_NOT_FOUND)
                .build();
        }
        return mapper.dtoFromDomain(user);
    }
}
