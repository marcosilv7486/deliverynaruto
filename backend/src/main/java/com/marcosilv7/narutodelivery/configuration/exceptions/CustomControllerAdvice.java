package com.marcosilv7.narutodelivery.configuration.exceptions;

import com.marcosilv7.narutodelivery.configuration.messages.MessageUtil;
import com.marcosilv7.narutodelivery.configuration.web.CustomFieldError;
import com.marcosilv7.narutodelivery.configuration.web.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class CustomControllerAdvice {

    private final MessageUtil messageUtil;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CustomControllerAdvice(MessageUtil messageUtil) {
        this.messageUtil = messageUtil;
    }

    @ExceptionHandler({ DisabledException.class, AccessDeniedException.class,AuthenticationServiceException.class})
    public ResponseEntity<ErrorResponse> errorSeguridad(RuntimeException e, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorResponse.setTimestamp(new Date());
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setError(messageUtil.findByCode(e.getMessage()));
        logger.info(errorResponse.getError());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ BusinessException.class })
    @ResponseStatus(value= HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleBusinessExepction(RuntimeException e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.CONFLICT.value());
        errorResponse.setTimestamp(new Date());
        errorResponse.setPath(request.getContextPath());
        errorResponse.setError(messageUtil.findByCode(e.getMessage()));
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldError = result.getFieldErrors();
        List<CustomFieldError> fieldErrorDTOS = populateFieldErrors(fieldError);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setTimestamp(new Date());
        errorResponse.setPath(request.getContextPath());
        errorResponse.setErrorsDetails(fieldErrorDTOS);
        errorResponse.setError("Se encontraron errores de validacion");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ EntityNotFoundException.class })
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> notFoundEntity(RuntimeException e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setTimestamp(new Date());
        errorResponse.setPath(request.getContextPath());
        errorResponse.setError(messageUtil.findByCode(e.getMessage()));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    private List<CustomFieldError> populateFieldErrors(List<FieldError> fieldErrorList) {
        List<CustomFieldError> fieldErrors = new ArrayList<CustomFieldError>();
        for (FieldError fieldError : fieldErrorList) {
            String localizedErrorMsg = messageUtil.findByCode(fieldError.getDefaultMessage());
            fieldErrors.add(new CustomFieldError(fieldError.getField(), localizedErrorMsg));
        }
        return fieldErrors;
    }

}
