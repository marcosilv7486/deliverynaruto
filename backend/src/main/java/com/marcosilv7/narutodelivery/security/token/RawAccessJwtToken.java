package com.marcosilv7.narutodelivery.security.token;

import com.marcosilv7.narutodelivery.configuration.exceptions.JwtExpiredTokenException;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.BadCredentialsException;

public class RawAccessJwtToken {

    private String token;

    public RawAccessJwtToken(String token) {
        this.token = token;
    }


    public Jws<Claims> parseClaims(String signingKey) {
        try {
            return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(this.token);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            throw new BadCredentialsException("Invalid JWT token: ", ex);
        } catch (ExpiredJwtException expiredEx) {
            throw new JwtExpiredTokenException("seguridad.error.token.expirado",expiredEx);
        }
    }

    public String getToken() {
        return this.token;
    }
}
