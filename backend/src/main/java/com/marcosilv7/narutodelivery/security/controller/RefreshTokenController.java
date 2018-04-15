package com.marcosilv7.narutodelivery.security.controller;

import com.marcosilv7.narutodelivery.configuration.api.Api;
import com.marcosilv7.narutodelivery.configuration.exceptions.JwtExpiredTokenException;
import com.marcosilv7.narutodelivery.configuration.security.WebSecurityConfig;
import com.marcosilv7.narutodelivery.security.authentication.login.UserContext;
import com.marcosilv7.narutodelivery.security.dto.UserDTO;
import com.marcosilv7.narutodelivery.security.exceptions.InvalidJwtToken;
import com.marcosilv7.narutodelivery.security.exceptions.JwtExpiredTokenRefreshException;
import com.marcosilv7.narutodelivery.security.service.interfaces.SecurityService;
import com.marcosilv7.narutodelivery.security.token.JwtDTO;
import com.marcosilv7.narutodelivery.security.token.RawAccessJwtToken;
import com.marcosilv7.narutodelivery.security.token.RefreshToken;
import com.marcosilv7.narutodelivery.security.token.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class RefreshTokenController {

    private final TokenUtil tokenUtil;
    private final SecurityService securityService;

    @Autowired
    public RefreshTokenController(TokenUtil tokenUtil, SecurityService securityService) {
        this.tokenUtil = tokenUtil;
        this.securityService = securityService;
    }

    @GetMapping(Api.REFRESH_TOKEN_PATH)
    public JwtDTO refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String tokenPayload = tokenUtil.extractToken(request.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM));

        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        try {
            RefreshToken refreshToken = RefreshToken.create(rawToken, TokenUtil.TOKEN_SIGNINGKEY).orElseThrow(()-> new InvalidJwtToken(""));
            String subject = refreshToken.getSubject();
            UserDTO user = securityService.findByUsername(subject).orElseThrow(() -> new UsernameNotFoundException("No se encontro el usuario: " + subject));
            if(!user.isEnabled()){
                throw new DisabledException("El Usuario esta deshabilitado");
            }
            if (user.getScopes() == null) throw new InsufficientAuthenticationException("El usuario no tiene privilegios asignados");
            List<GrantedAuthority> authorities = user.getScopes().stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getNameSpring()))
                    .collect(Collectors.toList());
            UserContext userContext = UserContext.create(user.getId(), user.getUsername(),user.getFullName(),user.getAvatar(),authorities);
            String token = tokenUtil.createAccessJwtToken(userContext);
            return new JwtDTO(token);
        }catch (JwtExpiredTokenException e){
            throw  new JwtExpiredTokenRefreshException(e.getMessage());
        }
    }
}
