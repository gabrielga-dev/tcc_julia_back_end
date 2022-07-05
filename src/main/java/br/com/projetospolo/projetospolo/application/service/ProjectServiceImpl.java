package br.com.projetospolo.projetospolo.application.service;

import br.com.projetospolo.projetospolo.domain.dto.ProjectDTO;
import br.com.projetospolo.projetospolo.domain.filter.ProjectFilter;
import br.com.projetospolo.projetospolo.domain.form.ProjectForm;
import br.com.projetospolo.projetospolo.domain.mapper.ProjectMapper;
import br.com.projetospolo.projetospolo.domain.repository.ProjectRepository;
import br.com.projetospolo.projetospolo.infrastructure.exception.BusinessException;
import br.com.projetospolo.projetospolo.infrastructure.service.ProjectService;
import br.com.projetospolo.projetospolo.util.ExceptionDescriptionConstants;
import br.com.projetospolo.projetospolo.util.ExceptionMessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl extends ProjectService {

    private final UserAuthService authService;

    private final ProjectMapper mapper;

    private final ProjectRepository repository;

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

        savedProject = mapper.transferInformation(savedProject, mapper.domainFromForm(projectForm));
        savedProject = repository.save(savedProject);

        return mapper.dtoFromDomain(savedProject);
    }

    @Override
    public Page<ProjectDTO> read(ProjectFilter filter, Pageable pageable) {
        var isProjectSituationNotNull = Optional.ofNullable(filter.getProjectSituation()).isPresent();
        return repository.filter(
                filter.getName(),
                isProjectSituationNotNull ? filter.getProjectSituation().name() : null,
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
                    .code(String.valueOf(HttpStatus.NOT_FOUND.value()))
                    .message(ExceptionMessageConstants.EXEPTION_MESSAGE_DATA_NOT_FOUND)
                    .description(ExceptionDescriptionConstants.EXEPTION_DESCRIPTION_PROJECT_NOT_FOUND)
                    .build()
            );
        return mapper.dtoFromDomain(savedProject);
    }
}
