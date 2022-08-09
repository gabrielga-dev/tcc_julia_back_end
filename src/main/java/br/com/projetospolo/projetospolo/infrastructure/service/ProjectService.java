package br.com.projetospolo.projetospolo.infrastructure.service;

import br.com.projetospolo.projetospolo.domain.dto.CommentDTO;
import br.com.projetospolo.projetospolo.domain.dto.ProjectDTO;
import br.com.projetospolo.projetospolo.domain.filter.ProjectFilter;
import br.com.projetospolo.projetospolo.domain.form.CommentForm;
import br.com.projetospolo.projetospolo.domain.form.ProjectForm;
import br.com.projetospolo.projetospolo.infrastructure.service.crud.CreateService;
import br.com.projetospolo.projetospolo.infrastructure.service.crud.DeleteService;
import br.com.projetospolo.projetospolo.infrastructure.service.crud.FindByIdService;
import br.com.projetospolo.projetospolo.infrastructure.service.crud.ReadService;
import br.com.projetospolo.projetospolo.infrastructure.service.crud.UpdateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public abstract class ProjectService
    implements
    CreateService<ProjectForm, ProjectDTO>,
    ReadService<ProjectFilter, ProjectDTO>,
    FindByIdService<Long, ProjectDTO>,
    UpdateService<ProjectForm, Long, ProjectDTO>,
    DeleteService<Long> {
    public abstract ProjectDTO updateParticipants(Long projectId, List<Long> participantsIds);

    public abstract Page<ProjectDTO> readProjectsWithoutComments(Integer page, Pageable of);

    public abstract Page<ProjectDTO> readCommentedProjects(Integer page, Pageable pageable);

    public abstract CommentDTO comment(CommentForm comment, Long projectId);
}
