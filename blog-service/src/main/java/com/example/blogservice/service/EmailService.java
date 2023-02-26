package com.example.blogservice.service;

import com.example.blogservice.models.RegistrationRequest;

public interface EmailService {

    void sendMail(String emailTo,String emailText);

}
