package br.com.projetospolo.projetospolo.infrastructure.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Vinícius Buttini
 */
@ControllerAdvice
public class ControlExceptionHandler {

    @ExceptionHandler(value = {BusinessException.class})
    protected ResponseEntity<Object> handleException(BusinessException ex, WebRequest request) {
        HttpHeaders responseHeaders = new HttpHeaders();
        return ResponseEntity.status(ex.getHttpStatusCode()).headers(responseHeaders).body(ex.getOnlyBody());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handleException(MethodArgumentNotValidException ex, WebRequest request) {
        HttpHeaders responseHeaders = new HttpHeaders();
        var errorMessages = ex.getAllErrors().stream()
            .map(
                error -> String.format(Objects.requireNonNull(error.getDefaultMessage()))
            ).collect(
                Collectors.joining("\n")
            );
        var toReturn = BusinessException.BusinessExceptionBody.builder()
            .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
            .message("Campo(s) inválido(s)!")
            .description(errorMessages)
            .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(responseHeaders).body(toReturn);
    }

}