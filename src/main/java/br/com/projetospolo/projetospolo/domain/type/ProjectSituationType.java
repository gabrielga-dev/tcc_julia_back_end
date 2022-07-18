package br.com.projetospolo.projetospolo.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProjectSituationType {

    NOT_STARTED("Não iniciado"),
    STARTED("Iniciado"),
    FINISHED("Finalizado");

    private final String description;
}
