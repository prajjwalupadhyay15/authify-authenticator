package com.prajjwal.authify.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.Year;


@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    @Value("${spring.mail.properties.mail.smtp.from}")
    private String fromEmail;

    public void sendWelcomeEmail(String toEmail,String name){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Welcome to our Platform!! Authify");
        message.setText("Hello "+name+",\n\n Thanks for registering with us!! \n\n Regards,\nAuthify Team");
        mailSender.send(message);
    }

//    public void sendResetOtpEmail(String toEmail,String otp){
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(fromEmail);
//        message.setTo(toEmail);
//        message.setSubject("Password Reset OTP");
//        message.setText("Your OTP for resetting your password is "+otp+".Use this OTP to proceed with resetting your password");
//        mailSender.send(message);
//    }
//    public void sendOtpEmail(String toEmail,String otp){
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(fromEmail);
//        message.setTo(toEmail);
//        message.setSubject("Account Verification OTP");
//        message.setText("Your OTP for Verifying your account is "+otp+".Use this OTP to verifying your account");
//        mailSender.send(message);


    public void sendOtpEmail(String toEmail, String userName, String otpCode) throws MessagingException {
        Context context = new Context();
        context.setVariable("userName", userName);
        context.setVariable("otpCode", otpCode);

        String htmlContent = templateEngine.process("verify-email", context); // Template file: verify-email.html

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setFrom(fromEmail);
        helper.setTo(toEmail);
        helper.setSubject("Account Verification OTP");
        helper.setText(htmlContent, true);

        mailSender.send(mimeMessage);
    }


    public void sendResetOtpEmail(String toEmail, String userName, String otpCode) throws MessagingException {
        Context context = new Context();
        context.setVariable("userName", userName);
        context.setVariable("otpCode", otpCode);
        context.setVariable("year", Year.now().getValue());

        String content = templateEngine.process("password-reset-email", context);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        helper.setFrom(fromEmail);
        helper.setTo(toEmail);
        helper.setSubject("Reset Your Password - OTP Inside");
        helper.setText(content, true);

        mailSender.send(mimeMessage);
    }

}
