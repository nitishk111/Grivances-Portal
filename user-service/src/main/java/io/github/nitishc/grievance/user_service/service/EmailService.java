package io.github.nitishc.grievance.user_service.service;

import io.github.nitishc.grievance.user_service.dto.EmailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(EmailDto emailDto){
        String to = emailDto.getTo();
        String body=emailDto.getBody();
        String subject=emailDto.getSubject();

        try{
            SimpleMailMessage mail= new SimpleMailMessage();
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(body);
            mailSender.send(mail);
        }catch (Exception e){
            log.error("Problem Sending mail", e.getMessage());
        }
    }
}

