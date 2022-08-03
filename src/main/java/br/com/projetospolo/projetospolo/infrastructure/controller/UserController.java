package br.com.projetospolo.projetospolo.infrastructure.controller;

import br.com.projetospolo.projetospolo.domain.dto.UserDTO;
import br.com.projetospolo.projetospolo.domain.form.UserForm;
import br.com.projetospolo.projetospolo.infrastructure.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.net.URI;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserForm user){

        var savedUser = userService.create(user);
        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(uri).body(savedUser);
    }

    @GetMapping
    public ResponseEntity<Page<UserDTO>> read(
        @PathVariable @Valid UserForm filter,
        Pageable pageable
    ){
        return ResponseEntity.ok(userService.read(filter, pageable));
    }

    @GetMapping("/left/{projectId}")
    public ResponseEntity<Page<UserDTO>> getAllUsersLeft(
        @PathVariable Long projectId,
        @RequestParam @Min(0) Integer page,
        @RequestParam @Min(5) Integer quantityPerPage
    ){
        return ResponseEntity.ok(userService.getAllUsersLeft(projectId, PageRequest.of(page, quantityPerPage)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(
        @PathVariable @Positive(message = "O valor do id deve ser positivo") @Valid Long id,
        @RequestBody @Valid UserForm user
    ){
        return ResponseEntity.ok(userService.update(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @PathVariable @Positive(message = "O valor do id deve ser positivo") @Valid Long id
    ){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
