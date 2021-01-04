package com.codeup.betterreads.services;

import com.codeup.betterreads.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("mailService")
public class MailService {

    @Autowired
    public JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    public String from;

    public void prepareAndSend(User user, String subject, String body) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("Better Reads <" + from + ">");
        msg.setTo(user.getEmail());
        msg.setSubject(subject);
        msg.setText(body);

        try {
            this.mailSender.send(msg);
        }
        catch (MailException ex){
            System.err.println(ex.getMessage());
        }
    }
}
