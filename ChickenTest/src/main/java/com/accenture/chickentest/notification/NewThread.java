package com.accenture.chickentest.notification;

import com.accenture.chickentest.service.ChickenService;
import com.accenture.chickentest.service.MailService;
import com.accenture.chickentest.service.TransformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Configuration
public class NewThread extends Thread {

    @Autowired
    public MailService mailService;
    @Autowired
    private ChickenService chickenService;

    public void run() {
        long startTime = System.currentTimeMillis();
        int i = 0;
        while (i<10) {
            System.out.println(this.getName() + ": New Thread is running..." + i++);
            try {

                System.out.println("enviando correo");
                mailService.sendEmail();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}