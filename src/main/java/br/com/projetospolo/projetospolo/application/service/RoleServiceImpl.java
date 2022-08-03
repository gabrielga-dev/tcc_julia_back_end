package br.com.projetospolo.projetospolo.application.service;

import br.com.projetospolo.projetospolo.domain.model.Role;
import br.com.projetospolo.projetospolo.domain.repository.RoleRepository;
import br.com.projetospolo.projetospolo.infrastructure.exception.BusinessException;
import br.com.projetospolo.projetospolo.infrastructure.service.RoleService;
import br.com.projetospolo.projetospolo.util.ExceptionDescriptionConstants;
import br.com.projetospolo.projetospolo.util.ExceptionMessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends RoleService {

    private final RoleRepository repository;

    @Override
    public Role findById(Long id) {
        return repository.findById(id)
            .orElseThrow(
                () -> BusinessException.builder()
                    .httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(ExceptionMessageConstants.EXEPTION_MESSAGE_DATA_NOT_FOUND)
                    .description(ExceptionDescriptionConstants.EXEPTION_DESCRIPTION_ROLE_NOT_FOUND)
                    .build()
            );
    }
}
