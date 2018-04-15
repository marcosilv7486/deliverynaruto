package com.marcosilv7.narutodelivery.security.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.util.Optional;

public class RefreshToken {

    private Jws<Claims> claims;

    private RefreshToken(Jws<Claims> claims) {
        this.claims = claims;
    }

    public static Optional<RefreshToken> create(RawAccessJwtToken token, String signingKey) {
        Jws<Claims> claims = token.parseClaims(signingKey);
        return Optional.of(new RefreshToken(claims));
    }

    public String getSubject() {
        return claims.getBody().getSubject();
    }
}
