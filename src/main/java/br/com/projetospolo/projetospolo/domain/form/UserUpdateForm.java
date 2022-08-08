package br.com.projetospolo.projetospolo.domain.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "O atributo nome não pode ser nulo.")
    @Size(min = 5, max = 30, message = "O campo de nome deve ter de 5 até 30 caracteres.")
    private String firstName;

    @NotNull(message = "O atributo sobrenome não pode ser nulo.")
    @Size(min = 5, max = 150, message = "O campo de sobrenome deve ter de 5 até 150 caracteres.")
    private String lastName;

    @NotNull(message = "O atributo email não pode ser nulo.")
    @Email(message = "O atributo email deve ser válido.")
    @Size(min = 8, max = 100, message = "O campo de email deve ter de 8 até 100 caracteres.")
    private String email;

    private String password;

    @NotNull(message = "A flag do tipo do usuário não pode ser nula.")
    private boolean intern;
}
