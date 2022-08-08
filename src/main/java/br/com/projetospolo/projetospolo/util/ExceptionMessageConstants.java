package br.com.projetospolo.projetospolo.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionMessageConstants {

    public static final String EXEPTION_MESSAGE_CREDENTIALS_ALREADY_IN_USE = "Erro nas credenciais!";
    public static final String EXEPTION_MESSAGE_DATA_NOT_FOUND = "Informação não encontrada!";
    public static final String EXEPTION_MESSAGE_UNAUTHORIZED_ACTION = "Você não possui permição para realizar tal operação.";
    public static final String EXEPTION_MESSAGE_UNACCEPTACLE_DATA = "Informações inválidas.";
    public static final String EXEPTION_MESSAGE_UNEXPECTED_ERROR = "Erro inesperado.";

}
