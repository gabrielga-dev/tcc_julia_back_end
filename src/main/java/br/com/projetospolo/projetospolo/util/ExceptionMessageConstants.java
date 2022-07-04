package br.com.projetospolo.projetospolo.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionMessageConstants {

    public static final String EXEPTION_MESSAGE_CREDENTIALS_ALREADY_IN_USE = "Tais credenciais já estão em uso.";
    public static final String EXEPTION_MESSAGE_DATA_NOT_FOUND = "Não foi encontrada a informação requerida.";
    public static final String EXEPTION_MESSAGE_UNAUTHORIZED_ACTION = "Você não possui permição para realizar tal operação.";

}
