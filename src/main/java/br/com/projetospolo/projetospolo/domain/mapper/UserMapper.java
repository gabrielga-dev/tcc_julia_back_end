package br.com.projetospolo.projetospolo.domain.mapper;

import br.com.projetospolo.projetospolo.domain.dto.RoleDTO;
import br.com.projetospolo.projetospolo.domain.dto.UserDTO;
import br.com.projetospolo.projetospolo.domain.form.UserForm;
import br.com.projetospolo.projetospolo.domain.model.User;
import br.com.projetospolo.projetospolo.infrastructure.mapper.DefaultMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper implements DefaultMapper<UserForm, UserDTO, User> {

    @Override
    public User domainFromForm(UserForm userForm) {
        return User.builder()
            .firstName(userForm.getFirstName())
            .lastName(userForm.getLastName())
            .email(userForm.getEmail())
            .password(userForm.getPassword())
            .build();
    }

    @Override
    public UserDTO dtoFromDomain(User user) {
        return UserDTO.builder()
            .id(user.getId())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .email(user.getEmail())
            .roles(
                user.getRoles().stream().map(
                    role -> new RoleDTO(role.getName())
                ).collect(Collectors.toSet())
            )
            .build();
    }

    @Override
    public User transferInformation(User source, User destiny) {
        destiny.setFirstName(source.getFirstName());
        destiny.setLastName(source.getLastName());
        destiny.setEmail(source.getEmail());
        return destiny;
    }
}
