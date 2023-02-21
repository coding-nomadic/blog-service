package com.example.blogservice.aop;

import com.example.blogservice.entity.AuditActivity;
import com.example.blogservice.entity.User;
import com.example.blogservice.repository.AuditRepository;
import com.example.blogservice.repository.UserRepository;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class AuditAspect {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuditRepository auditRepository;

    @After("@annotation(audit)")
    public void auditData(Audit audit) {
        try {
            String userName = System.getProperty("username");
            User user = userRepository.findByUserName(userName)
                                            .orElseThrow(() -> new UsernameNotFoundException("User Name not found"));
            AuditActivity auditActivity = new AuditActivity();
            auditActivity.setApi(audit.api());
            auditActivity.setOperation(audit.operation());
            auditActivity.setEmail(user.getEmail());
            auditActivity.setUsername(userName);
            auditActivity.setDate(new Date());
            auditRepository.save(auditActivity);
            log.info("Audited for API and OPERATION {} {}", audit.api(), audit.operation());
        } catch (Exception exception) {
            log.error("Error occurred while auditing data {}", exception.getLocalizedMessage());
        }
    }
}
