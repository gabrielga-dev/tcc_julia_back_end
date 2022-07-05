package br.com.projetospolo.projetospolo.application.service;

import br.com.projetospolo.projetospolo.domain.dto.UserDTO;
import br.com.projetospolo.projetospolo.domain.form.UserForm;
import br.com.projetospolo.projetospolo.domain.mapper.UserMapper;
import br.com.projetospolo.projetospolo.domain.repository.UserRepository;
import br.com.projetospolo.projetospolo.domain.type.RoleType;
import br.com.projetospolo.projetospolo.infrastructure.exception.BusinessException;
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

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends UserService {

    private final UserAuthService authService;
    private final RoleService roleService;

    private final UserRepository repository;

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
        toSave.setPassword(new BCryptPasswordEncoder().encode(toSave.getPassword()));
        toSave.setCreationDate(LocalDateTime.now());
        toSave.setRoles(
            Set.of(
                roleService.findById(
                    userForm.isInterno()
                        ? RoleType.INTERNO.getId()
                        : RoleType.EXTERNO.getId()
                )
            )
        );

        var saved = repository.save(toSave);

        return mapper.dtoFromDomain(saved);
    }

    @Override
    public UserDTO update(Long id, UserForm userForm) {
        var saved = repository.findById(id)
            .orElseThrow(
                () -> BusinessException.builder()
                    .httpStatusCode(HttpStatus.BAD_REQUEST)
                    .message(ExceptionMessageConstants.EXEPTION_MESSAGE_DATA_NOT_FOUND)
                    .description(ExceptionDescriptionConstants.EXEPTION_DESCRIPTION_USER_NOT_FOUND)
                    .build()
            );

        var authenticatedUser = authService.getAuth();
        if(!Objects.equals(authenticatedUser.getUsername(), saved.getUsername())){
            throw BusinessException.builder()
                .httpStatusCode(HttpStatus.UNAUTHORIZED)
                .message(ExceptionMessageConstants.EXEPTION_MESSAGE_UNAUTHORIZED_ACTION)
                .description(ExceptionDescriptionConstants.EXEPTION_DESCRIPTION_CANNOT_UPDATE_OTHER_USER)
                .build();
        }

        var updater = mapper.domainFromForm(userForm);
        if(
            !Objects.equals(saved.getUsername(), updater.getUsername()) &&
                repository.findByEmail(updater.getUsername()).isPresent()
        ){
            throw BusinessException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(ExceptionMessageConstants.EXEPTION_MESSAGE_CREDENTIALS_ALREADY_IN_USE)
                .description(ExceptionDescriptionConstants.EXEPTION_DESCRIPTION_CREDENTIALS_ALREADY_IN_USE)
                .build();
        }

        saved = mapper.transferInformation(updater, saved);

        var updated = repository.save(saved);
        return mapper.dtoFromDomain(updated);
    }

    @Override
    public Page<UserDTO> read(UserForm filter, Pageable pageable) {
        return null;//todo implementar
    }

    @Override
    public void delete(Long id) {

    }

}
