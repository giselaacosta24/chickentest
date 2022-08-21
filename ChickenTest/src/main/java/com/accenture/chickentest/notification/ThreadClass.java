package com.accenture.chickentest.notification;


import com.accenture.chickentest.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class ThreadClass extends Thread {


    @Autowired
    private NotificationService notificationService;
    private long initialTime;

    private MailSender mailSender;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    private SimpleMailMessage templateMessage;


    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }
    @Override
    public void run(){
        System.out.println("La cajera   COMIENZA A PROCESAR LA COMPRA DEL CLIENTE "
                + " EN EL TIEMPO: "
                + (System.currentTimeMillis() - this.initialTime) / 1000
                + "seg");

         esperarXsegundos(1);
        SimpleMailMessage message = new SimpleMailMessage(templateMessage);
        message.setFrom("giseacosta651@gmail.com");
        message.setTo("giseacosta651@gmail.com");
        message.setSubject("urgente");
        message.setText("prueba");
        System.out.println(message);
        mailSender.send(message);
        try {
            this.mailSender.send(message);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());

        }

    }


    private void esperarXsegundos(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }


}




