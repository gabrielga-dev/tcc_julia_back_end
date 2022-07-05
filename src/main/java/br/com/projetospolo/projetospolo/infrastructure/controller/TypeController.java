package br.com.projetospolo.projetospolo.infrastructure.controller;

import br.com.projetospolo.projetospolo.domain.type.ProjectSituationType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/type")
public class TypeController {

    @GetMapping("/project-situation")
    public ResponseEntity<List<ProjectSituationType>> getProjectSituationValues(){
        return ResponseEntity.ok(List.of(ProjectSituationType.values()));
    }
}
