package br.com.projetospolo.projetospolo.infrastructure.controller;

import br.com.projetospolo.projetospolo.domain.dto.ProjectDTO;
import br.com.projetospolo.projetospolo.domain.dto.UserDTO;
import br.com.projetospolo.projetospolo.domain.filter.ProjectFilter;
import br.com.projetospolo.projetospolo.domain.form.ProjectForm;
import br.com.projetospolo.projetospolo.domain.form.UserForm;
import br.com.projetospolo.projetospolo.domain.type.ProjectSituationType;
import br.com.projetospolo.projetospolo.infrastructure.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.Date;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/project")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectDTO> create(@RequestBody @Valid ProjectForm project){

        var savedProject = projectService.create(project);
        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedProject.getId()).toUri();
        return ResponseEntity.created(uri).body(savedProject);
    }

    @GetMapping
    public ResponseEntity<Page<ProjectDTO>> read(
        @RequestParam String name,
        @RequestParam ProjectSituationType projectSituation,
        @RequestParam Date startDate,
        @RequestParam Date endDate,
        Pageable pageable
    ){
        var filter = ProjectFilter.builder()
            .name(name)
            .projectSituation(projectSituation)
            .startDate(startDate)
            .endDate(endDate)
            .build();
        return ResponseEntity.ok(projectService.read(filter, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(projectService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> update(
        @PathVariable @Positive(message = "O valor do id deve ser positivo") @Valid Long id,
        @RequestBody @Valid ProjectForm projectForm
    ){
        return ResponseEntity.ok(projectService.update(id, projectForm));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @PathVariable @Positive(message = "O valor do id deve ser positivo") @Valid Long id
    ){
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
