package br.com.projetospolo.projetospolo.domain.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AuthForm {

    @NotNull(message = "O atributo email não pode ser nulo.")
    @Email(message = "O atributo email deve ser válido.")
    @Size(min = 8, max = 100, message = "O campo de email deve ter de 8 até 100 caracteres.")
    private String email;

    @NotNull(message = "O atributo senha não pode ser nulo.")
    @Size(min = 8, max = 100, message = "O campo de senha deve ter de 8 até 100 caracteres.")
    private String password;

    public UsernamePasswordAuthenticationToken convert() {
        return new UsernamePasswordAuthenticationToken(email,password);
    }
}
