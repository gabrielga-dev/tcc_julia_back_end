package br.com.projetospolo.projetospolo.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionDescriptionConstants {

    public static final String EXEPTION_DESCRIPTION_CREDENTIALS_ALREADY_IN_USE = "Tais credenciais já estão em uso.";
    public static final String EXEPTION_DESCRIPTION_CANNOT_UPDATE_OTHER_USER = "Você não pode editar os dados de outra pessoa, apenas os seus.";
    public static final String EXEPTION_DESCRIPTION_CANNOT_UPDATE_OTHER_USERS_PROJECT = "Você não pode editar esse proejto pois você não é o responsável por ele.";
    public static final String EXEPTION_DESCRIPTION_CANNOT_DO_THIS = "Você não possui permissão para realizar tal ação.";
    public static final String EXEPTION_DESCRIPTION_USER_NOT_FOUND = "Não foi encontrado um usuário com tais credenciais.";
    public static final String EXEPTION_DESCRIPTION_PROJECT_NOT_FOUND = "Não foi encontrado um projeto com tais credenciais.";
    public static final String EXEPTION_DESCRIPTION_ROLE_NOT_FOUND = "Não foi encontrada uma role com tais credenciais.";

    public static String EXEPTION_DESCRIPTION_PROJECT_MAX_PARTICIPANTS_EXCEEDED(Integer maximun, Integer current) {
        return String.format(
            "Para o projeto em questão, é permitido até %d participantes, foi requisitado %d.",
            maximun, current
        );
    }

}
