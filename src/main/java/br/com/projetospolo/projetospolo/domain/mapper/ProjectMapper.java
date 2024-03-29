package br.com.projetospolo.projetospolo.domain.mapper;

import br.com.projetospolo.projetospolo.domain.dto.ProjectDTO;
import br.com.projetospolo.projetospolo.domain.form.ProjectForm;
import br.com.projetospolo.projetospolo.domain.model.Project;
import br.com.projetospolo.projetospolo.infrastructure.mapper.DefaultMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProjectMapper implements DefaultMapper<ProjectForm, ProjectDTO, Project> {

    private final UserMapper userMapper;
    private final CommentMapper commentMapper;

    @Override
    public Project domainFromForm(ProjectForm projectForm) {
        return Project.builder()
            .name(projectForm.getName())
            .description(projectForm.getDescription())
            .projectSituation(projectForm.getProjectSituation())
            .endDate(projectForm.getEndDate())
            .maxParticipants(projectForm.getMaxParticipants())
            .startDate(projectForm.getStartDate())
            .build();
    }

    @Override
    public ProjectDTO dtoFromDomain(Project project) {
        return ProjectDTO.builder()
            .id(project.getId())
            .name(project.getName())
            .description(project.getDescription())
            .projectSituation(project.getProjectSituation().getDescription())
            .endDate(project.getEndDate())
            .maxParticipants(project.getMaxParticipants())
            .startDate(project.getStartDate())
            .owner(userMapper.dtoFromDomain(project.getOwner()))
            .participants(
                project.getParticipants().parallelStream().map(
                    userMapper::dtoFromDomain
                ).collect(Collectors.toSet())
            )
            .comments(
                project.getComments().parallelStream().map(
                    commentMapper::dtoFromDomain
                ).collect(Collectors.toList())
            )
            .build();
    }

    @Override
    public Project transferInformation(Project source, Project destiny) {
        destiny.setName(source.getName());
        destiny.setName(source.getName());
        destiny.setDescription(source.getDescription());
        destiny.setMaxParticipants(source.getMaxParticipants());
        destiny.setProjectSituation(source.getProjectSituation());
        destiny.setStartDate(source.getStartDate());
        destiny.setEndDate(source.getEndDate());
        return destiny;
    }
}
