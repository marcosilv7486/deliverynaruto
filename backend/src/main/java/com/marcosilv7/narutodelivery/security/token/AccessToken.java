package com.marcosilv7.narutodelivery.security.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.Claims;

public class AccessToken {

    private final String rawToken;
    @JsonIgnore
    private Claims claims;

    public AccessToken(final String token, Claims claims) {
        this.rawToken = token;
        this.claims = claims;
    }

    public String getToken() {
        return this.rawToken;
    }

    public Claims getClaims() {
        return claims;
    }

}
