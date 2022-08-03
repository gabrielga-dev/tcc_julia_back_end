package br.com.projetospolo.projetospolo.infrastructure.service;

import br.com.projetospolo.projetospolo.domain.dto.UserDTO;
import br.com.projetospolo.projetospolo.domain.form.UserForm;
import br.com.projetospolo.projetospolo.infrastructure.service.crud.CRUDService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public abstract class UserService implements CRUDService<UserForm, UserDTO, Long, UserForm> {

    public abstract Page<UserDTO> getAllUsersLeft(Long projectId, Pageable pageable);
}
