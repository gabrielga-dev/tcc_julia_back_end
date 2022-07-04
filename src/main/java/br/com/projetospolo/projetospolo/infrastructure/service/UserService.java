package br.com.projetospolo.projetospolo.infrastructure.service;

import br.com.projetospolo.projetospolo.domain.dto.UserDTO;
import br.com.projetospolo.projetospolo.domain.form.UserForm;
import br.com.projetospolo.projetospolo.infrastructure.service.crud.CRUDService;

public abstract class UserService implements CRUDService<UserForm, UserDTO, Long, UserForm> {
}
