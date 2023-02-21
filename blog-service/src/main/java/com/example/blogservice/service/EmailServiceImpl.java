package com.example.blogservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    @Async
    public void sendMail(String emailTo, String emailText) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(emailText, true);
            helper.setTo(emailTo);
            helper.setSubject("Confirm your email");
            helper.setFrom("tnzngdw@gmail.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Failed to send email for: " + emailTo + "\n" + e.getLocalizedMessage());
            throw new IllegalArgumentException("Failed to send email for: " + emailTo);
        }

    }

}
