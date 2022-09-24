package com.myproject.onlinecourses.dto;

import lombok.Data;
import org.springframework.context.annotation.Bean;


public class ResponseObject {
    private String errorCode;
    private String status;
    private String message;
    private Object data;

    public ResponseObject(String errorCode, String status, String message, Object data){
        this.errorCode = errorCode;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResponseObject(Object data){
        this.errorCode = "";
        this.status = "200";
        this.message = "";
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
