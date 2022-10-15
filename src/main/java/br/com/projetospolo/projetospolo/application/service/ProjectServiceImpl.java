package br.com.projetospolo.projetospolo.application.service;

import br.com.projetospolo.projetospolo.domain.dto.CommentDTO;
import br.com.projetospolo.projetospolo.domain.dto.ProjectDTO;
import br.com.projetospolo.projetospolo.domain.filter.ProjectFilter;
import br.com.projetospolo.projetospolo.domain.form.CommentForm;
import br.com.projetospolo.projetospolo.domain.form.ProjectForm;
import br.com.projetospolo.projetospolo.domain.mapper.CommentMapper;
import br.com.projetospolo.projetospolo.domain.mapper.ProjectMapper;
import br.com.projetospolo.projetospolo.domain.model.Comment;
import br.com.projetospolo.projetospolo.domain.model.User;
import br.com.projetospolo.projetospolo.domain.repository.CommentRepository;
import br.com.projetospolo.projetospolo.domain.repository.ProjectRepository;
import br.com.projetospolo.projetospolo.domain.repository.UserRepository;
import br.com.projetospolo.projetospolo.domain.type.ProjectSituationType;
import br.com.projetospolo.projetospolo.infrastructure.exception.BusinessException;
import br.com.projetospolo.projetospolo.infrastructure.service.ProjectService;
import br.com.projetospolo.projetospolo.util.ExceptionDescriptionConstants;
import br.com.projetospolo.projetospolo.util.ExceptionMessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl extends ProjectService {

    private final UserAuthService authService;

    private final ProjectMapper mapper;
    private final CommentMapper commentMapper;

    private final ProjectRepository repository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public ProjectDTO create(ProjectForm projectForm) {
        var toSave = mapper.domainFromForm(projectForm);

        toSave.setOwner(authService.getAuth());
        toSave.setParticipants(Set.of());

        var saved = repository.save(toSave);

        return mapper.dtoFromDomain(saved);
    }

    @Override
    public ProjectDTO update(Long id, ProjectForm projectForm) {
        var savedProject = repository.findById(id).orElseThrow(
            () -> BusinessException.builder()
                .httpStatusCode(HttpStatus.NOT_FOUND)
                .message(ExceptionMessageConstants.EXEPTION_MESSAGE_DATA_NOT_FOUND)
                .description(ExceptionDescriptionConstants.EXEPTION_DESCRIPTION_PROJECT_NOT_FOUND)
                .build()
        );
        var authenticatedUser = authService.getAuth();

        if (!Objects.equals(savedProject.getOwner().getId(), authenticatedUser.getId())) {
            throw BusinessException.builder()
                .httpStatusCode(HttpStatus.UNAUTHORIZED)
                .message(ExceptionMessageConstants.EXEPTION_MESSAGE_UNAUTHORIZED_ACTION)
                .description(ExceptionDescriptionConstants.EXEPTION_DESCRIPTION_CANNOT_UPDATE_OTHER_USERS_PROJECT)
                .build();
        }

        savedProject = mapper.transferInformation(mapper.domainFromForm(projectForm), savedProject);
        savedProject = repository.save(savedProject);

        return mapper.dtoFromDomain(savedProject);
    }

    @Override
    public Page<ProjectDTO> read(ProjectFilter filter, Pageable pageable) {
        var isSituationPresent = Objects.nonNull(filter.getProjectSituation());
        var optSituation = ProjectSituationType.getProjectSituationTypeByDescription(filter.getProjectSituation());
        ProjectSituationType situation = null;

        if (isSituationPresent && optSituation.isPresent()) {
            situation = optSituation.get();
        }

        return repository.filter(
                filter.getId(),
                filter.getName(),
                situation,
                filter.getStartDate(),
                filter.getEndDate(),
                pageable
            )
            .map(mapper::dtoFromDomain);
    }

    @Override
    public void delete(Long id) {
        var savedProject = repository.findByIdAndOwner_Id(
            id, authService.getAuth().getId()
        ).orElseThrow(
            () -> BusinessException.builder()
                .httpStatusCode(HttpStatus.NOT_FOUND)
                .code(String.valueOf(HttpStatus.NOT_FOUND.value()))
                .message(ExceptionMessageConstants.EXEPTION_MESSAGE_DATA_NOT_FOUND)
                .description(ExceptionDescriptionConstants.EXEPTION_DESCRIPTION_PROJECT_NOT_FOUND)
                .build()
        );
        repository.delete(savedProject);
    }

    @Override
    public ProjectDTO findById(Long id) {
        var savedProject = repository.findById(id)
            .orElseThrow(
                () -> BusinessException.builder()
                    .httpStatusCode(HttpStatus.NOT_FOUND)
                    .code(String.valueOf(HttpStatus.NOT_FOUND.value()))
                    .message(ExceptionMessageConstants.EXEPTION_MESSAGE_DATA_NOT_FOUND)
                    .description(ExceptionDescriptionConstants.EXEPTION_DESCRIPTION_PROJECT_NOT_FOUND)
                    .build()
            );
        return mapper.dtoFromDomain(savedProject);
    }

    @Override
    public ProjectDTO updateParticipants(Long projectId, List<Long> participantsIds) {
        var savedProject = repository.findById(projectId).orElseThrow(
            () -> BusinessException.builder()
                .httpStatusCode(HttpStatus.NOT_FOUND)
                .code(String.valueOf(HttpStatus.NOT_FOUND.value()))
                .message(ExceptionMessageConstants.EXEPTION_MESSAGE_DATA_NOT_FOUND)
                .description(ExceptionDescriptionConstants.EXEPTION_DESCRIPTION_PROJECT_NOT_FOUND)
                .build()
        );

        if (savedProject.getMaxParticipants().compareTo(participantsIds.size()) < 0) {
            throw BusinessException.builder()
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .message(ExceptionMessageConstants.EXEPTION_MESSAGE_UNACCEPTACLE_DATA)
                .description(
                    ExceptionDescriptionConstants.EXEPTION_DESCRIPTION_PROJECT_MAX_PARTICIPANTS_EXCEEDED(
                        savedProject.getMaxParticipants(), participantsIds.size()
                    )
                )
                .build();
        }

        var newParticipants = userRepository.findAllById(participantsIds)
            .stream()
            .filter(User::isEnabled)
            .collect(Collectors.toList());

        savedProject.getParticipants().clear();
        savedProject.setParticipants(new HashSet<>(newParticipants));
        savedProject = repository.save(savedProject);

        return mapper.dtoFromDomain(savedProject);
    }

    @Override
    public Page<ProjectDTO> readProjectsWithoutComments(Integer page, Pageable pageable) {
        var authenticatedUser = authService.getAuth();
        return repository.findProjectsWithoutComments(
            authenticatedUser.getId(), pageable
        ).map(mapper::dtoFromDomain);
    }

    @Override
    public Page<ProjectDTO> readCommentedProjects(Integer page, Pageable pageable) {
        var authenticatedUser = authService.getAuth();
        return repository.findCommentedProjects(
            authenticatedUser.getId(), pageable
        ).map(mapper::dtoFromDomain);
    }

    @Override
    public CommentDTO comment(CommentForm comment, Long projectId) {
        var project = repository.findById(projectId)
            .orElseThrow(
                () -> BusinessException.builder()
                    .httpStatusCode(HttpStatus.NOT_FOUND)
                    .code(String.valueOf(HttpStatus.NOT_FOUND.value()))
                    .message(ExceptionMessageConstants.EXEPTION_MESSAGE_DATA_NOT_FOUND)
                    .description(ExceptionDescriptionConstants.EXEPTION_DESCRIPTION_PROJECT_NOT_FOUND)
                    .build()
            );
        var authenticatedUser = authService.getAuth();
        var isUserParticipant = project.getParticipants()
            .stream()
            .map(User::getId)
            .anyMatch(id -> authenticatedUser.getId().compareTo(id) == 0);

        if (!isUserParticipant){
            throw BusinessException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .message(ExceptionMessageConstants.EXEPTION_MESSAGE_UNAUTHORIZED_ACTION)
                .description(ExceptionDescriptionConstants.EXEPTION_DESCRIPTION_CANNOT_COMMENT_ON_THIS_PROJECT)
                .build();
        }

        var hasUserCommented = project.getComments()
            .stream()
            .map(Comment::getAuthor)
            .map(User::getId)
            .anyMatch(id -> authenticatedUser.getId().compareTo(id) == 0);

        if (hasUserCommented){
            throw BusinessException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .message(ExceptionMessageConstants.EXEPTION_MESSAGE_UNAUTHORIZED_ACTION)
                .description(ExceptionDescriptionConstants.EXEPTION_DESCRIPTION_CANNOT_COMMENT_MORE_THAN_ONE_TIME)
                .build();
        }

        var toSave = commentMapper.domainFromForm(comment);
        toSave.setProject(project);
        toSave.setCreationDate(new Date());
        toSave.setAuthor(authenticatedUser);

        return commentMapper.dtoFromDomain(commentRepository.save(toSave));
    }
}
