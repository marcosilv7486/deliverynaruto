package com.marcosilv7.narutodelivery.security.authentication.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcosilv7.narutodelivery.configuration.messages.MessageUtil;
import com.marcosilv7.narutodelivery.configuration.web.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper mapper;
    private final MessageUtil messageUtil;

    @Autowired
    public CustomAuthenticationFailureHandler(ObjectMapper mapper, MessageUtil messageUtil) {
        this.mapper = mapper;
        this.messageUtil = messageUtil;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorResponse.setTimestamp(new Date());
        errorResponse.setPath(httpServletRequest.getRequestURI());
        errorResponse.setError(messageUtil.findByCode(e.getMessage()));
        mapper.writeValue(httpServletResponse.getWriter(),errorResponse);
    }
}
