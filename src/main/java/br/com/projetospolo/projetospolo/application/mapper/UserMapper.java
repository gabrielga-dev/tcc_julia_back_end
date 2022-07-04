package br.com.projetospolo.projetospolo.application.mapper;

import br.com.projetospolo.projetospolo.domain.dto.UserDTO;
import br.com.projetospolo.projetospolo.domain.form.UserForm;
import br.com.projetospolo.projetospolo.domain.model.User;
import br.com.projetospolo.projetospolo.infrastructure.mapper.DefaultMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements DefaultMapper<UserForm, UserDTO, User> {

    @Override
    public User domainFromForm(UserForm userForm) {
        return User.builder()
            .firstName(userForm.getFirstName())
            .lastName(userForm.getLastName())
            .password(userForm.getPassword())
            .build();
    }

    @Override
    public UserDTO dtoFromDomain(User user) {
        return null;
    }

    @Override
    public User transferInformation(User source, User destiny) {
        destiny.setFirstName(source.getFirstName());
        destiny.setLastName(source.getLastName());
        destiny.setEmail(source.getEmail());
        return destiny;
    }
}
