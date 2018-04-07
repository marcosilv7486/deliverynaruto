package com.marcosilv7.narutodelivery.security.authentication.jwt;

import com.marcosilv7.narutodelivery.security.authentication.login.UserContext;
import com.marcosilv7.narutodelivery.security.token.RawAccessJwtToken;
import com.marcosilv7.narutodelivery.security.token.TokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();
        Jws<Claims> jwsClaims = rawAccessToken.parseClaims(TokenUtil.TOKEN_SIGNINGKEY);
        String subject = jwsClaims.getBody().getSubject();
        List<String> scopes = jwsClaims.getBody().get(TokenUtil.SCOPES, List.class);
        Long userId=jwsClaims.getBody().get(TokenUtil.USER_ID,Long.class);
        String userFullName = jwsClaims.getBody().get(TokenUtil.USER_FULLNAME,String.class);
        List<GrantedAuthority> authorities = scopes.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        UserContext context = UserContext.create(userId,subject,userFullName,authorities);
        return new JwtAuthenticationToken(context, context.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return (JwtAuthenticationToken.class.isAssignableFrom(aClass));
    }
}
