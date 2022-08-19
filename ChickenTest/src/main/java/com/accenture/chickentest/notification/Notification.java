package com.accenture.chickentest.notification;

import com.accenture.chickentest.domain.dto.ChickenDTO;
import com.accenture.chickentest.service.ChickenService;
import com.accenture.chickentest.service.NotificationService;
import com.lowagie.text.DocumentException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Notification extends Thread {
    private ChickenService chickenService;

    public void run(HttpServletResponse response) throws DocumentException, IOException {
        long startTime = System.currentTimeMillis();
        int i = 0;
        while (true) {
            System.out.println(this.getName() + ": New Thread is running..." + i++);
            try {
                response.setContentType("application/pdf");
                DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
                String currentDateTime = dateFormatter.format(new Date());

                String headerKey = "Content-Disposition";
                String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
                response.setHeader(headerKey, headerValue);

                List<ChickenDTO> listChickens = chickenService.getChickens();

                NotificationService exporter = new NotificationService(listChickens);
                exporter.export(response);                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
