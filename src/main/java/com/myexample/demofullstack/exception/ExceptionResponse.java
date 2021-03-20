package com.myexample.demofullstack.exception;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ExceptionResponse {
    private HttpStatus status;
    private String message;
    private Date timeStamp;

    public ExceptionResponse() {
    }

    public ExceptionResponse(HttpStatus status, String message, Date timeStamp) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
