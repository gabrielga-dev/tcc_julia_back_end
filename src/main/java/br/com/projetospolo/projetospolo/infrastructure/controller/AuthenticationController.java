package br.com.projetospolo.projetospolo.infrastructure.controller;

import br.com.projetospolo.projetospolo.application.service.TokenService;
import br.com.projetospolo.projetospolo.application.service.UserAuthService;
import br.com.projetospolo.projetospolo.domain.dto.AuthDTO;
import br.com.projetospolo.projetospolo.domain.form.AuthForm;
import br.com.projetospolo.projetospolo.infrastructure.exception.BusinessException;
import br.com.projetospolo.projetospolo.util.ExceptionDescriptionConstants;
import br.com.projetospolo.projetospolo.util.ExceptionMessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    private final UserAuthService userAuthService;

    @PostMapping
    public ResponseEntity<AuthDTO> autenticate(@RequestBody AuthForm form){
        try {
            var authentication = authenticationManager.authenticate(form.convert());
            var token = tokenService.generateToken(authentication);
            return ResponseEntity.ok(new AuthDTO(token, "Bearer"));
        }catch (AuthenticationException e){
            throw BusinessException.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST)
                .message(ExceptionMessageConstants.EXEPTION_MESSAGE_DATA_NOT_FOUND)
                .description(ExceptionDescriptionConstants.EXEPTION_DESCRIPTION_USER_NOT_FOUND)
                .build();
        }
    }
}
