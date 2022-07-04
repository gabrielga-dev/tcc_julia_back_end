package br.com.projetospolo.projetospolo.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<RoleDTO> roles;
}
