package br.com.projetospolo.projetospolo.infrastructure.controller;

import br.com.projetospolo.projetospolo.domain.dto.EnumDTO;
import br.com.projetospolo.projetospolo.domain.type.ProjectSituationType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/type")
public class TypeController {

    @GetMapping("/project-situation")
    public ResponseEntity<Set<EnumDTO>> getProjectSituationValues(){
        var toReturn = Stream.of(ProjectSituationType.values()).map(
            type -> new EnumDTO(type.getDescription(), type.name())
        ).collect(Collectors.toSet());
        return ResponseEntity.ok(toReturn);
    }
}
