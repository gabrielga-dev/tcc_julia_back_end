package br.com.projetospolo.projetospolo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthDTO {

    private String token;

    private String type;
}
