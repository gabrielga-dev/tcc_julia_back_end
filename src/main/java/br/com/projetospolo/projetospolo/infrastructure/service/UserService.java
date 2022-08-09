package br.com.projetospolo.projetospolo.infrastructure.service;

import br.com.projetospolo.projetospolo.domain.dto.UserDTO;
import br.com.projetospolo.projetospolo.domain.filter.UserFilter;
import br.com.projetospolo.projetospolo.domain.form.UserForm;
import br.com.projetospolo.projetospolo.domain.form.UserUpdateForm;
import br.com.projetospolo.projetospolo.infrastructure.service.crud.CreateService;
import br.com.projetospolo.projetospolo.infrastructure.service.crud.DeleteService;
import br.com.projetospolo.projetospolo.infrastructure.service.crud.FindByIdService;
import br.com.projetospolo.projetospolo.infrastructure.service.crud.ReadService;
import br.com.projetospolo.projetospolo.infrastructure.service.crud.UpdateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public abstract class UserService
    implements CreateService<UserForm, UserDTO>,
    UpdateService<UserUpdateForm, Long, UserDTO>,
    ReadService<UserFilter, UserDTO>,
    DeleteService<Long>,
    FindByIdService<Long, UserDTO>
{

    public abstract Page<UserDTO> getAllUsersLeft(Long projectId, Pageable pageable);
}
