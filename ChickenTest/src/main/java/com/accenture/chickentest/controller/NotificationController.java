package com.accenture.chickentest.controller;

import com.accenture.chickentest.domain.dto.ChickenDTO;
import com.accenture.chickentest.service.ChickenService;
import com.accenture.chickentest.service.NotificationService;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin({"http://localhost:4200"})
@RequestMapping("/api/v1/notification")
public class NotificationController {

    @Autowired
    private ChickenService chickenService;

    @GetMapping("/chickens/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<ChickenDTO> listChickens = chickenService.getChickens();

        NotificationService exporter = new NotificationService(listChickens);
        exporter.export(response);

    }
}
