package com.example.blogservice.service;

import com.example.blogservice.models.RegistrationRequest;

public interface RegistrationService {

    /**
     *
     * @param registrationRequest
     */
    public void registerUser(RegistrationRequest registrationRequest);
}
