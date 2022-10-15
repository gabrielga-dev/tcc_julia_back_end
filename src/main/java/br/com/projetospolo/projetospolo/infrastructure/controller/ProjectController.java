package br.com.projetospolo.projetospolo.infrastructure.controller;

import br.com.projetospolo.projetospolo.domain.dto.CommentDTO;
import br.com.projetospolo.projetospolo.domain.dto.ProjectDTO;
import br.com.projetospolo.projetospolo.domain.filter.ProjectFilter;
import br.com.projetospolo.projetospolo.domain.form.CommentForm;
import br.com.projetospolo.projetospolo.domain.form.ProjectForm;
import br.com.projetospolo.projetospolo.infrastructure.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/project")
public class ProjectController {

    private static final Integer MAX_PROJECTS_PER_PAGE = 20;
    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectDTO> create(@RequestBody @Valid ProjectForm project) {

        var savedProject = projectService.create(project);
        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedProject.getId()).toUri();
        return ResponseEntity.created(uri).body(savedProject);
    }

    @GetMapping
    public ResponseEntity<Page<ProjectDTO>> read(
        @RequestParam(required = false) Long id,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String projectSituation,
        @RequestParam(required = false) Date startDate,
        @RequestParam(required = false) Date endDate,
        @RequestParam @Min(0) Integer page
    ) {
        var filter = ProjectFilter.builder()
            .id(id)
            .name(name)
            .projectSituation(projectSituation)
            .startDate(startDate)
            .endDate(endDate)
            .build();
        return ResponseEntity.ok(projectService.read(filter, PageRequest.of(page, MAX_PROJECTS_PER_PAGE)));
    }

    @GetMapping("/without-comments")
    public ResponseEntity<Page<ProjectDTO>> readProjectsWithoutComments(
        @RequestParam @Min(0) Integer page
    ) {
        return ResponseEntity.ok(
            projectService.readProjectsWithoutComments(page, PageRequest.of(page, MAX_PROJECTS_PER_PAGE))
        );
    }

    @GetMapping("/commented")
    public ResponseEntity<Page<ProjectDTO>> readCommentedProjects(
        @RequestParam @Min(0) Integer page
    ) {
        return ResponseEntity.ok(
            projectService.readCommentedProjects(page, PageRequest.of(page, MAX_PROJECTS_PER_PAGE))
        );
    }

    @PostMapping("/comment/{id}")
    public ResponseEntity<CommentDTO> comment(
        @PathVariable("id") Long projectId,
        @RequestBody @Valid CommentForm comment
        ) {
        return ResponseEntity.ok(projectService.comment(comment, projectId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> update(
        @PathVariable @Positive(message = "O valor do id deve ser positivo") @Valid Long id,
        @RequestBody @Valid ProjectForm projectForm
    ) {
        return ResponseEntity.ok(projectService.update(id, projectForm));
    }

    @PutMapping("/{id}/participants")
    public ResponseEntity<ProjectDTO> updateParticipants(
        @PathVariable @Positive(message = "O valor do id deve ser positivo") @Valid Long id,
        @RequestBody List<Long> participantsIds
    ) {
        return ResponseEntity.ok(projectService.updateParticipants(id, participantsIds));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @PathVariable @Positive(message = "O valor do id deve ser positivo") @Valid Long id
    ) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
