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
import java.util.Timer;
import java.util.TimerTask;
import  java.util.concurrent.ScheduledThreadPoolExecutor;


public class Notification extends Thread {
    private ChickenService chickenService;

    TimerTask task = new TimerTask() {
        public void run() {
            System.out.println("Task performed on: " + new Date() + "n"
                    + "Thread's name: " + Thread.currentThread().getName());
        }
    };
    Timer timer = new Timer("Timer");
    long delay = 1000L;
//timer.schedule(task, delay);
   /* public void run(HttpServletResponse response) throws DocumentException, IOException {
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
    }*/

}
