package com.marcosilv7.narutodelivery.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class JwtExpiredTokenRefreshException extends AuthenticationException {
    public JwtExpiredTokenRefreshException(String message) {
        super(message);
    }
}
