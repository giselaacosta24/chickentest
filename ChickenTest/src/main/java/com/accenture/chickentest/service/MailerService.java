package com.accenture.chickentest.service;



import com.accenture.chickentest.domain.dto.TransactionDTO;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

@Service
public class MailerService {



    @Autowired
    private JavaMailSender emailSender;



    List<TransactionDTO> transactions;


    @Autowired
    private TransactionService transactionService;



    public MailerService(){

    }
    public MailerService(List<TransactionDTO> transactions){
        this.transactions=transactions;

    }

    @Async
    public void sendNotification() throws MailException, InterruptedException, MessagingException {

        System.out.println("Sleeping now...");

        Document doc = new Document();
        try {
            PdfWriter document = PdfWriter.getInstance(doc, new FileOutputStream("src//main//resources//static//reporte.pdf"));
            System.out.println("PDF created.");
            doc.open();
            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            font.setSize(18);
            font.setColor(Color.BLUE);

            Paragraph p = new Paragraph("Estado de Granja", font);
            p.setAlignment(Paragraph.ALIGN_CENTER);
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100f);
            table.setWidths(new float[]  {1.5f, 3.5f, 3.0f, 3.0f});
            table.setSpacingBefore(10);


            writeTableHeader(table);
            writeTableData(table);

            doc.add(p);

            doc.add(table);

            doc.close();
            document.close();
            Thread.sleep(10000);
            System.out.println("Sending email...");

            ClassPathResource pdf = new ClassPathResource("static/reporte.pdf");
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

            message.setSubject("Capacidad de granja limitada");
            helper = new MimeMessageHelper(message, true);
            helper.setFrom("giseacosta651@gmail.com");
            helper.setTo("giseacosta651@gmail.com");
            String htmlMsg = "<h3>Detalle de movimientos de la granja adjunto</h3>"
                   ;


            helper.addAttachment("reporte.pdf", pdf);
            helper.setText(htmlMsg, true);
            emailSender.send(message);
        }
           catch (DocumentException e)
            {
                e.printStackTrace();
            }
        catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }



        System.out.println("Email Sent!");
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
        List<TransactionDTO> transactions= transactionService.getTransactions();
        for (TransactionDTO transaction : transactions) {
            table.addCell(String.valueOf(transaction.getTypeProduct()));
            table.addCell(String.valueOf(transaction.getPrice()));
            table.addCell(String.valueOf(transaction.getTypeTransaction()));
            table.addCell(String.valueOf(transaction.getDateTransaction()));

        }
    }

}
