package br.com.projetospolo.projetospolo.domain.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CommentForm {

    @NotNull(message = "O atributo título não pode ser nulo.")
    @Size(min = 3, max = 100, message = "O campo de título deve ter de 3 até 100 caracteres.")
    private String title;


    @NotNull(message = "O atributo conteúdo não pode ser nulo.")
    @Size(min = 5, max = 5000, message = "O campo de conteúdo deve ter de 5 até 5000 caracteres.")
    private String content;
}
