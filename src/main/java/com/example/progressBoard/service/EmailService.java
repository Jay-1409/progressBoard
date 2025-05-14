package com.example.progressBoard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private AuthService authService;
    public boolean sendSimpleMail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("sjay140905@gmail.com");
        message.setTo(to);
        message.setSubject("Test mail");
        message.setText("This is a test mail");
        try {
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            System.out.println("Could not send email to: " + to + ": "+ e.getMessage());
        }
        return false;
    }
    public boolean sendOtpMail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("sjay140905@gmail.com");
        message.setTo(to);
        message.setSubject("Otp for progressBoard");
        int otp = authService.generateOtp(to);
        System.out.println("Otp for email: " + to + " :" + otp);
        message.setText("Enter this code to login: " + otp);
        try {
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            System.out.println("Could not send email to: " + to + ": "+ e.getMessage());
        }
        return false;
    }
}
