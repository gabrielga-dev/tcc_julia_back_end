package br.com.projetospolo.projetospolo.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum ProjectSituationType {

    NOT_STARTED("Não iniciado"),
    STARTED("Iniciado"),
    FINISHED("Finalizado");

    private static final Map<String, ProjectSituationType> DESCRIPTION_VALUE_MAP = Arrays.stream(
        ProjectSituationType.values()
    ).collect(
        Collectors.toMap(
            ProjectSituationType::getDescription,
            Function.identity()
        )
    );

    private final String description;

    public static Optional<ProjectSituationType> getProjectSituationTypeByDescription(String description) {
        return Optional.ofNullable(DESCRIPTION_VALUE_MAP.get(description));
    }
}
