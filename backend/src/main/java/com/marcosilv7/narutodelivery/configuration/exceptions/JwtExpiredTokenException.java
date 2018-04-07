package com.marcosilv7.narutodelivery.configuration.exceptions;


import org.springframework.security.core.AuthenticationException;

public class JwtExpiredTokenException extends AuthenticationException {
    public JwtExpiredTokenException(String message,Throwable t){
        super(message,t);
    }
}
