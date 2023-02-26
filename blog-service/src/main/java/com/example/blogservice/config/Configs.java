package com.example.blogservice.config;

import com.example.blogservice.service.UserDetailServiceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configs {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public UserDetailServiceImpl userDetailServiceImpl() {
        return new UserDetailServiceImpl();
    }
}
