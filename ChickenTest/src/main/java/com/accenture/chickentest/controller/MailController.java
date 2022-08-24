package com.accenture.chickentest.controller;

import com.accenture.chickentest.service.MailerService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
public class MailController {
    private static final Logger logger = LoggerFactory.getLogger(MailController.class);

    @Autowired
    private MailerService mailerService;


    @RequestMapping("send-mail-async")
    public String sendMailAsync() {

        try {
            mailerService.sendNotification();
        } catch (Exception e) {
            // catch error
            logger.info("Error Sending Email: " + e.getMessage());
        }
        return "Thank you for registering with us.";
    }
}