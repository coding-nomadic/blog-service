package com.example.blogservice.service;

import com.example.blogservice.entity.Role;
import com.example.blogservice.entity.User;
import com.example.blogservice.exceptions.BlogServiceException;
import com.example.blogservice.exceptions.UserNotFoundException;
import com.example.blogservice.models.RegistrationRequest;
import com.example.blogservice.repository.RoleRepository;
import com.example.blogservice.repository.UserRepository;
import com.example.blogservice.utils.EmailUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    EmailService emailService;

    @Override
    public void registerUser(RegistrationRequest registrationRequest) {
        if (userRepository.existsByUserName(registrationRequest.getUserName())) {
            throw new BlogServiceException("Username already exists!", "104");
        }
        if (userRepository.existsByEmail(registrationRequest.getEmail())) {
            throw new BlogServiceException("Email already exists!", "104");
        }
        String tokenForNewUser = UUID.randomUUID().toString();
        User user = user(registrationRequest, tokenForNewUser);
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
        String link = "http://localhost:8888/api/v1/signup/confirm?token=" + tokenForNewUser;
        emailService.sendMail(registrationRequest.getEmail(),
                                        EmailUtils.buildEmail(registrationRequest.getFullName(), link));

    }

    /**
     * 
     * @param registrationRequest
     * @return
     */
    private User user(RegistrationRequest registrationRequest, String token) {
        User user = new User();
        user.setUserName(registrationRequest.getUserName());
        user.setFullName(registrationRequest.getFullName());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setMobileNumber(registrationRequest.getMobileNumber());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setToken(token);
        user.setEnabled(false);
        return user;
    }

    @Override
    public String confirmToken(String token) {
        if (userRepository.existsByToken(token)) {
            log.info("Token exists in the user DB, hence updating the isEnabled parameter");
        } else {
            throw new UserNotFoundException("Token not found for the user");
        }
        User user = userRepository.findByToken(token).orElseThrow(() -> new UserNotFoundException("Not found"));
        user.setEnabled(true);
        userRepository.save(user);
        log.info("isEnabled Parameter is updated in DB");
        return "Your email is confirmed. Thank you for using our service!";
    }
}
