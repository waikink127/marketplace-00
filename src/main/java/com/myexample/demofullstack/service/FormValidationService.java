package com.myexample.demofullstack.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FormValidationService {
    public ResponseEntity<?> formValidation(BindingResult result){
        List<String> message = new ArrayList<>();
        Map<String, List<String>> errors = new HashMap<>();
        for (FieldError error : result.getFieldErrors()){
            message.add(error.getDefaultMessage());
            errors.put("error", message);
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
