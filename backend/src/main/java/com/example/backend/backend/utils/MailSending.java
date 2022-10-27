package com.example.backend.backend.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.backend.backend.collections.User;

@Service
public class MailSending {
    @Autowired
    private JavaMailSender javaMailSender;
    public String sendSimpleMail(User user,String message){
        try{
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setFrom("Avishkar2k22Team@gmail.com");
        mailMessage.setTo(user.getEmail());
        System.out.println(message);
        mailMessage.setText(message);
        mailMessage.setSubject("Registration");
        javaMailSender.send(mailMessage);
        return "Successfully sent";
    }
    catch(Exception e){
         System.out.println(e.getMessage());
        return e.getMessage();
        
    }
   }
}