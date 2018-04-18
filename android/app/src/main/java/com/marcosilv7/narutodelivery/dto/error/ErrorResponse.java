package com.marcosilv7.narutodelivery.dto.error;

import java.util.Date;
import java.util.List;

public class ErrorResponse {
    private Date timestamp;
    private Integer status;
    private String error;
    private String path;

    private List<FieldError> errorsDetails;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<FieldError> getErrorsDetails() {
        return errorsDetails;
    }

    public void setErrorsDetails(List<FieldError> errorsDetails) {
        this.errorsDetails = errorsDetails;
    }

    public String obtenerMensajeError(){
        StringBuilder mensaje= new StringBuilder();
        if(errorsDetails!=null && !errorsDetails.isEmpty()){
            for(FieldError fieldError : errorsDetails){
                mensaje.append(fieldError.getFieldError());
                mensaje.append(" ");
            }
            return mensaje.toString();
        }else {
            return error;
        }
    }
}
