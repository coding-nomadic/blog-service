package com.example.blogservice.controller;

import com.example.blogservice.models.LoginRequest;
import com.example.blogservice.models.TokenResponse;
import com.example.blogservice.utils.JsonUtils;
import com.example.blogservice.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
public class AuthenticationController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(path = "/authenticate")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) throws IOException {
        log.info("Request Body for Login {}", JsonUtils.toString(loginRequest));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        log.info("Authentication Successful !");
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(jwtUtil.generateToken(loginRequest.getUsername()));
        return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
    }
}
