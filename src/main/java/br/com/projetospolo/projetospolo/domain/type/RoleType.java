package br.com.projetospolo.projetospolo.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleType {
    INTERNO(1L),
    EXTERNO(2L);

    private final Long id;

    public static RoleType getRoleTypeByInternFlag(boolean flag) {
        return flag ? RoleType.INTERNO : RoleType.EXTERNO;
    }
}
