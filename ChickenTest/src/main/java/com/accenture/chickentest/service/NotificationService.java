package com.accenture.chickentest.service;


import com.accenture.chickentest.domain.dto.TransactionDTO;
import com.accenture.chickentest.repository.TransactionRepository;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.springframework.stereotype.Service;



import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.*;
import java.util.List;



import com.lowagie.text.DocumentException;


@Service
public class NotificationService {




   private TransactionRepository transactionRepository;
    List<TransactionDTO> transactions;
    public NotificationService(){



    }
    public NotificationService(List<TransactionDTO> transactions){
        this.transactions=transactions;

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
        System.out.println("PDF created.");
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
