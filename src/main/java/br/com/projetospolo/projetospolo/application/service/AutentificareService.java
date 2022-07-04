package br.com.projetospolo.projetospolo.application.service;

import br.com.projetospolo.projetospolo.domain.repository.UserRepository;
import br.com.projetospolo.projetospolo.infrastructure.exception.BusinessException;
import br.com.projetospolo.projetospolo.util.ExceptionDescriptionConstants;
import br.com.projetospolo.projetospolo.util.ExceptionMessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutentificareService implements UserDetailsService {

    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username)
            .orElseThrow(
                ()-> BusinessException.builder()
                    .httpStatusCode(HttpStatus.BAD_REQUEST)
                    .message(ExceptionMessageConstants.EXEPTION_MESSAGE_DATA_NOT_FOUND)
                    .description(ExceptionDescriptionConstants.EXEPTION_DESCRIPTION_USER_NOT_FOUND)
                    .build()
            );
    }

}