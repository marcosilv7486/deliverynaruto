package com.marcosilv7.narutodelivery.security.authentication.login;

import com.marcosilv7.narutodelivery.configuration.exceptions.NoSuchUserScopeException;
import com.marcosilv7.narutodelivery.security.dao.projections.UserSummaryProjection;
import com.marcosilv7.narutodelivery.security.service.interfaces.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final BCryptPasswordEncoder encoder;
    private final SecurityService securityService;

    @Autowired
    public CustomAuthenticationProvider(BCryptPasswordEncoder encoder, SecurityService securityService) {
        this.encoder = encoder;
        this.securityService = securityService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        UserSummaryProjection user = securityService.findByUsername(username).orElseThrow(()
                -> new BadCredentialsException("seguridad.error.credenciales.incorrectos"));

        if(!user.isEnabled()){
            throw new DisabledException("seguridad.error.usuario.deshabilitado");
        }

        if (!encoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("seguridad.error.credenciales.incorrectos");
        }

        if(user.getScopes() == null ||user.getScopes().isEmpty()){
            throw new NoSuchUserScopeException("seguridad.error.usuario.permisos");
        }

        List<GrantedAuthority> authorities = user.getScopes().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getScope()))
                .collect(Collectors.toList());
        UserContext userContext = UserContext.create(user.getId(),user.getUsername(),user.getFullName(),authorities);
        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
