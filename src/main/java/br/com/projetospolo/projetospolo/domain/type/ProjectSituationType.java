package br.com.projetospolo.projetospolo.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProjectSituationType {

    NOT_STARTED("Não iniciado"),
    STARTED("Iniciado"),
    FINISHED("Finalziado");

    private final String description;
}
