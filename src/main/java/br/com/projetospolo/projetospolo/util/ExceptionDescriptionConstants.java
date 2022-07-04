package br.com.projetospolo.projetospolo.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionDescriptionConstants {

    public static final String EXEPTION_DESCRIPTION_CREDENTIALS_ALREADY_IN_USE = "Tais credenciais já estão em uso.";
    public static final String EXEPTION_DESCRIPTION_CANNOT_UPDATE_OTHER_USER = "Você não pode editar os dados de outra pessoa, apenas os seus.";
    public static final String EXEPTION_DESCRIPTION_USER_NOT_FOUND = "Não foi encontrada um usuário com tais credenciais.";
    public static final String EXEPTION_DESCRIPTION_ROLE_NOT_FOUND = "Não foi encontrada uma role com tais credenciais.";

}
