package com.accenture.chickentest.service;

import com.accenture.chickentest.domain.dto.ChickenDTO;
import com.accenture.chickentest.domain.dto.EggDTO;
import com.accenture.chickentest.domain.dto.TransactionDTO;
import com.accenture.chickentest.repository.TransactionRepository;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.Properties;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
public class NotificationService {

    private List<TransactionDTO> transactions;



    private MailSender emailSender;

   private TransactionRepository transactionRepository;
    public NotificationService(){



    }
    public NotificationService(List<TransactionDTO> transactions){
        this.transactions=transactions;

    }




    public void
    sendMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("giseacosta651@gmail.com");
        message.setTo("giseacosta651@gmail.com");
        message.setSubject("urgente");
        message.setText("prueba");
        emailSender.send(message);
    /*    MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo("giseacosta651@gmail.com");

        helper.setSubject("Alerta:Capacidad llegó al limite");
        msg.setContent("sb", "text/html; charset=utf-8");*/

    /*    List<ChickenDTO> chickens=chickenService.getChickensWithFarm(8L);
        List<EggDTO> eggs=eggService.getEggsWithFarm(8L);*/

        /*String sb = "<head>" +
                "<style type=\"text/css\">" +
                "  .red { color: #f00; }" +
                "</style>" +
                "</head>" +
         /*       "<h1 class=\"red\">" + msg.getSubject() + "</h1>" +
                "<h2>Cantidad de Pollos" +chickens.stream().count()+"</h2>"+
                "<h2>Cantidad de Huevos" +eggs.stream().count()+"</h2>";*/


        //javaMailSender.send(msg);

    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(4);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase(" Tipo de Producto", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Precio", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Tipo de Transaccion", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Fecha de Transaccion", font));
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table) {
        for (TransactionDTO transaction : transactions) {
            table.addCell(String.valueOf(transaction.getTypeProduct()));
            table.addCell(String.valueOf(transaction.getPrice()));
            table.addCell(String.valueOf(transaction.getTypeTransaction()));
            table.addCell(String.valueOf(transaction.getDateTransaction()));

        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("Estado de Granja", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);


        document.add(p);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]  {1.5f, 3.5f, 3.0f, 3.0f});
        table.setSpacingBefore(10);


        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }





}
