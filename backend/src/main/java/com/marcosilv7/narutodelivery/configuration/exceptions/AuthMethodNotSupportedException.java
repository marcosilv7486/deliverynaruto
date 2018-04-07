package com.marcosilv7.narutodelivery.configuration.exceptions;

import org.springframework.security.core.AuthenticationException;

public class AuthMethodNotSupportedException extends AuthenticationException {
    public AuthMethodNotSupportedException(String message){
        super(message);
    }
}
