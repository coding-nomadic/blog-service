package com.example.blogservice.models;

import lombok.Data;

import javax.persistence.Column;

@Data
public class RegistrationRequest {
    private String fullName;
    private String userName;
    private String email;
    private String password;
    private String mobileNumber;
}
