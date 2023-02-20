package com.example.blogservice.controller;

import com.example.blogservice.models.RegistrationRequest;
import com.example.blogservice.service.RegistrationService;
import com.example.blogservice.utils.JsonUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/api/v1/signup")
@Slf4j
public class RegistrationController {

    @Autowired
    RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<Void> signUp(@RequestBody RegistrationRequest registrationRequest) throws IOException {
        log.info("Entered Registration Request {}", JsonUtils.toString(registrationRequest));
        registrationService.registerUser(registrationRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
