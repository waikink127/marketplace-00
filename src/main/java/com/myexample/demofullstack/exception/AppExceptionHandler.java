package com.myexample.demofullstack.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> usernameAlreadyExist(UserAlreadyExistException exc, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(exc.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setTimeStamp(new Date());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> generalNotFoundExceptionHandling(GeneralNotFoundException exc, WebRequest request){
        ExceptionResponse response = new ExceptionResponse();
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setTimeStamp(new Date());
        response.setMessage(exc.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> illegalStateExceptionHandler(IllegalStateException exc, WebRequest request){
        ExceptionResponse response = new ExceptionResponse();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        response.setTimeStamp(new Date());
        response.setMessage(exc.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
