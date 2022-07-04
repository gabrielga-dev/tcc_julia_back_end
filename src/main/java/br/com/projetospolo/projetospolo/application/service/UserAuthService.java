package br.com.projetospolo.projetospolo.application.service;

import br.com.projetospolo.projetospolo.domain.dto.UserDTO;
import br.com.projetospolo.projetospolo.domain.mapper.UserMapper;
import br.com.projetospolo.projetospolo.domain.model.User;
import br.com.projetospolo.projetospolo.domain.repository.UserRepository;
import br.com.projetospolo.projetospolo.infrastructure.exception.BusinessException;
import br.com.projetospolo.projetospolo.util.ExceptionDescriptionConstants;
import br.com.projetospolo.projetospolo.util.ExceptionMessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserAuthService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    public User getAuth(){
        return userRepository.findByEmail(
            ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()
        ).orElseThrow(
            () -> BusinessException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(ExceptionMessageConstants.EXEPTION_MESSAGE_DATA_NOT_FOUND)
                .description(ExceptionDescriptionConstants.EXEPTION_DESCRIPTION_USER_NOT_FOUND)
                .build()
        );
    }

    public UserDTO getAuthenticatedUser(){
        return mapper.dtoFromDomain(userRepository.findByEmail(
            ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()
        ).orElseThrow(
            () -> BusinessException.builder()
                .code(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                .message(ExceptionMessageConstants.EXEPTION_MESSAGE_UNAUTHORIZED_ACTION)
                .description(ExceptionDescriptionConstants.EXEPTION_DESCRIPTION_CANNOT_DO_THIS)
                .build()
        ));
    }
}
