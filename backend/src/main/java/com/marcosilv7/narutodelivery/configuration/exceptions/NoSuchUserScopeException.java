package com.marcosilv7.narutodelivery.configuration.exceptions;

import org.springframework.security.core.AuthenticationException;

public class NoSuchUserScopeException extends AuthenticationException {
    public NoSuchUserScopeException(String message){
        super(message);
    }
}
