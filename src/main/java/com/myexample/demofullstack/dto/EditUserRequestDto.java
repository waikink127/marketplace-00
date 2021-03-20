package com.myexample.demofullstack.dto;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;


public class EditUserRequestDto {

    @Email(message = "Please enter valid email address")
    private String email;
    @Size(min = 8,max = 8, message = "Please enter valid phone number")
    private String phone;

    public EditUserRequestDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
