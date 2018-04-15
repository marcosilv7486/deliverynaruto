package com.marcosilv7.narutodelivery.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtToken extends AuthenticationException {
    public InvalidJwtToken(String s) {
        super(s);
    }
}
