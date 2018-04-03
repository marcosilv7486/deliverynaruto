package com.marcosilv7.narutodelivery.configuration.web;

public class CustomFieldError {
    private String fieldName;
    private String fieldError;

    public CustomFieldError(String fieldName, String fieldError) {
        this.fieldName = fieldName;
        this.fieldError = fieldError;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldError() {
        return fieldError;
    }

    public void setFieldError(String fieldError) {
        this.fieldError = fieldError;
    }
}
