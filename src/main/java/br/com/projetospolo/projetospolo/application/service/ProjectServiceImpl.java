package br.com.projetospolo.projetospolo.application.service;

import br.com.projetospolo.projetospolo.domain.dto.ProjectDTO;
import br.com.projetospolo.projetospolo.domain.form.ProjectForm;
import br.com.projetospolo.projetospolo.infrastructure.service.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl extends ProjectService {

    @Override
    public ProjectDTO create(ProjectForm projectForm) {
        return null;
    }

    @Override
    public ProjectDTO update(Long id, ProjectForm projectForm) {
        return null;
    }

    @Override
    public Page<ProjectDTO> read(ProjectForm filter, Pageable pageable) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
