package com.accenture.chickentest.controller;

import com.accenture.chickentest.domain.dto.TransactionDTO;
import com.accenture.chickentest.service.NotificationService;
import com.accenture.chickentest.service.TransactionService;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin({"http://localhost:4200"})
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private NotificationService notificationService;
   @GetMapping("/pdf")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
    response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Reporte-" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        List<TransactionDTO> transactions = transactionService.getTransactions();

        NotificationService exporter = new NotificationService(transactions);

        exporter.export(response);

   }


}
