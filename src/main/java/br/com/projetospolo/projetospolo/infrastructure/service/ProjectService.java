package br.com.projetospolo.projetospolo.infrastructure.service;

import br.com.projetospolo.projetospolo.domain.dto.ProjectDTO;
import br.com.projetospolo.projetospolo.domain.filter.ProjectFilter;
import br.com.projetospolo.projetospolo.domain.form.ProjectForm;
import br.com.projetospolo.projetospolo.infrastructure.service.crud.CreateService;
import br.com.projetospolo.projetospolo.infrastructure.service.crud.DeleteService;
import br.com.projetospolo.projetospolo.infrastructure.service.crud.FindByIdService;
import br.com.projetospolo.projetospolo.infrastructure.service.crud.ReadService;
import br.com.projetospolo.projetospolo.infrastructure.service.crud.UpdateService;

public abstract class ProjectService
    implements
    CreateService<ProjectForm, ProjectDTO>,
    ReadService<ProjectFilter, ProjectDTO>,
    FindByIdService<Long, ProjectDTO>,
    UpdateService<ProjectForm, Long, ProjectDTO>,
    DeleteService<Long>
{
}
