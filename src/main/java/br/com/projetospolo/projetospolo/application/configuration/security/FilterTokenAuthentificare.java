package br.com.projetospolo.projetospolo.application.configuration.security;

import br.com.projetospolo.projetospolo.application.service.TokenService;
import br.com.projetospolo.projetospolo.domain.repository.UserRepository;
import br.com.projetospolo.projetospolo.infrastructure.exception.BusinessException;
import br.com.projetospolo.projetospolo.util.ExceptionDescriptionConstants;
import br.com.projetospolo.projetospolo.util.ExceptionMessageConstants;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@AllArgsConstructor
public class FilterTokenAuthentificare extends OncePerRequestFilter {

    private TokenService tokenService;

    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = retriveToken(request);
        if (tokenService.isValidToken(token)){
            authenticateUser(token);
        }
        filterChain.doFilter(request,response);
    }

    private void authenticateUser(String token) {
        var userId = tokenService.getUserId(token);
        var user = userRepository.findById(userId)
            .orElseThrow(
                () -> BusinessException.builder()
                    .httpStatusCode(HttpStatus.BAD_REQUEST)
                    .message(ExceptionMessageConstants.EXEPTION_MESSAGE_DATA_NOT_FOUND)
                    .description(ExceptionDescriptionConstants.EXEPTION_DESCRIPTION_USER_NOT_FOUND)
                    .build()
            );
        UsernamePasswordAuthenticationToken authenticarion = new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticarion);
    }

    private String retriveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (ObjectUtils.isEmpty(token) || !token.startsWith("Bearer ")){
            return null;
        }
        return token.substring(7);
    }
}