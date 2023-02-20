package com.example.blogservice.service;

import com.example.blogservice.entity.Role;
import com.example.blogservice.entity.User;
import com.example.blogservice.exceptions.BlogServiceException;
import com.example.blogservice.models.RegistrationRequest;
import com.example.blogservice.repository.RoleRepository;
import com.example.blogservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(RegistrationRequest registrationRequest) {
        // add check for username exists in database
        if (userRepository.existsByUsername(registrationRequest.getUserName())) {
            throw new BlogServiceException("Username is already exists!", "104");
        }
        // add check for email exists in database
        if (userRepository.existsByEmail(registrationRequest.getEmail())) {
            throw new BlogServiceException("Email is already exists!", "104");
        }
        User user=user(registrationRequest);
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);

    }

    private User user(RegistrationRequest registrationRequest) {
        User user = new User();
        user.setUserName(registrationRequest.getUserName());
        user.setFullName(registrationRequest.getFullName());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(registrationRequest.getPassword());
        user.setMobileNumber(registrationRequest.getMobileNumber());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        return user;
    }
}
