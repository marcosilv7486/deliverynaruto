package com.marcosilv7.narutodelivery.security.token;

import com.marcosilv7.narutodelivery.configuration.exceptions.NoSuchUserScopeException;
import com.marcosilv7.narutodelivery.security.authentication.login.UserContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TokenUtil {

    public static final String HEADER_PREFIX="Beaber ";
    public static final String SCOPES = "scopes";
    public static final String USER_ID = "userId";
    public static final String USER_NAME = "user_name";
    public static final String USER_FULLNAME = "user_fullname";
    public static final String USER_AVATAR = "user_avatar";
    public static final int EXPIRATION_TIME=36000;
    public static final String TOKEN_ISSUER="rumboalmundial2018";
    public static final String TOKEN_SIGNINGKEY="AQUIVAUNAFIRMA";

    public String extractToken(String header) {
        if (StringUtils.isBlank(header)) {
            throw new AuthenticationServiceException("Authorization header no puede estar en blanco");
        }

        if (header.length() < HEADER_PREFIX.length()) {
            throw new AuthenticationServiceException("El header de Authorization no es valido");
        }
        return header.substring(HEADER_PREFIX.length(), header.length());
    }


    public String createAccessJwtToken(UserContext userContext) {
        if (StringUtils.isBlank(userContext.getUserName()))
            throw new IllegalArgumentException("El nombre de usuario es requerido");

        if (userContext.getAuthorities() == null || userContext.getAuthorities().isEmpty())
            throw new NoSuchUserScopeException("seguridad.error.usuario.sin.privilegios");

        Claims claims = Jwts.claims().setSubject(userContext.getUserName());
        claims.put(SCOPES, userContext.getAuthorities().stream().map(Object::toString).collect(Collectors.toList()));
        claims.put(USER_ID,userContext.getUserId());
        claims.put(USER_NAME,userContext.getUserName());
        claims.put(USER_FULLNAME,userContext.getFullName());
        claims.put(USER_AVATAR,userContext.getAvatar());
        DateTime currentTime = new DateTime();
        Date fechaVencimiento=currentTime.plusMinutes(EXPIRATION_TIME).toDate();
        String jti=UUID.randomUUID().toString();
        return Jwts.builder()
                .setClaims(claims)
                .setId(jti)
                .setIssuer(TOKEN_ISSUER)
                .setIssuedAt(currentTime.toDate())
                .setExpiration(fechaVencimiento)
                .signWith(SignatureAlgorithm.HS512, TOKEN_SIGNINGKEY)
                .compact();
    }


    public String createRefreshToken(UserContext userContext) {
        if (StringUtils.isBlank(userContext.getUserName())) {
            throw new IllegalArgumentException("El nombre de usuario es requerido");
        }

        DateTime currentTime = new DateTime();
        Date fechaVencimiento=currentTime.plusMinutes(EXPIRATION_TIME).toDate();
        Claims claims = Jwts.claims().setSubject(userContext.getUserName());
        claims.put(SCOPES, userContext.getAuthorities().stream().map(Object::toString).collect(Collectors.toList()));
        String jti= UUID.randomUUID().toString();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(TOKEN_ISSUER)
                .setId(jti)
                .setIssuedAt(currentTime.toDate())
                .setExpiration(fechaVencimiento)
                .signWith(SignatureAlgorithm.HS512, TOKEN_SIGNINGKEY)
                .compact();
    }
}
