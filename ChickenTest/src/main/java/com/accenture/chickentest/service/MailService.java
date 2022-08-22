package com.accenture.chickentest.service;


import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@Service
public class MailService {
    private JavaMailSender emailSender;


    public void setMailSender(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Async
   public void sendEmail() {

        try {

            MimeMessage message = emailSender.createMimeMessage();

            message.setSubject("Capacidad de granja limitada");
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(message, true);
            helper.setFrom("giseacosta651@gmail.com");
            helper.setTo("giseacosta651@gmail.com");
            String htmlMsg = "<h3>Hello World!</h3>";

            helper.setText(htmlMsg, true);
            emailSender.send(message);
        } catch (MessagingException ex) {
            System.out.println("Ocurrio un error al enviar correo");
        }
    }
}
