package com.cruise.utils;

import com.cruise.model.entities.Cruise;
import com.cruise.model.entities.Ticket;
import com.cruise.model.entities.User;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * PDF builder util. Generates tickets in pdf format.
 *
 * @author Vasyl Utrysko.
 * @version 1.0
 */
public class TicketBuilder {

    private TicketBuilder(){}
    private static final String TICKET_PDF_TITLE = "Ticket #";
    private static final String TICKET_PDF_PASSENGER_INFO = "Passenger info:";

    /**
     * Creates and put into response tickets in pdf format
     *
     * @param response - HttpResponse
     * @param user - user instance
     * @param cruise - cruise instance
     * @param ticket - ticket instance
     */
    public static void ticketPdf(HttpServletResponse response, User user, Cruise cruise, Ticket ticket) {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, baos);
            document.open();

            document.addTitle(TICKET_PDF_TITLE);
            Font fontTitle = new Font();
            fontTitle.setSize(24);
            Paragraph title = new Paragraph(TICKET_PDF_TITLE + ticket.getId(), fontTitle);
            title.setAlignment(1);
            title.setSpacingBefore(35);
            title.setSpacingAfter(35);
            document.add(title);

            Font fontPassengerInfo = new Font();
            fontPassengerInfo.setSize(14);
            Paragraph infoTitle = new Paragraph(TICKET_PDF_PASSENGER_INFO, fontPassengerInfo);
            infoTitle.setAlignment(1);
            infoTitle.setSpacingBefore(10);
            document.add(infoTitle);
            Paragraph passengerInfo = new Paragraph("Full name: " + user.getFirstName() + " " + user.getLastName()
            + "\n" + "Email: " + user.getEmail(), fontPassengerInfo);
            passengerInfo.setSpacingAfter(40);
            document.add(passengerInfo);

            PdfPTable tableFirst = new PdfPTable(3);
            Font tableFont = new Font();
            tableFont.setSize(14);

            PdfPCell cell1 = new PdfPCell(new Phrase("Ship Name", tableFont));
            tableFirst.addCell(cell1);

            PdfPCell cell2 = new PdfPCell(new Phrase("Start Date", tableFont));
            tableFirst.addCell(cell2);

            PdfPCell cell3 = new PdfPCell(new Phrase("End Date", tableFont));
            tableFirst.addCell(cell3);

            PdfPTable tableSecond = new PdfPTable(3);
            PdfPCell cell4 = new PdfPCell(new Phrase("Start port", tableFont));
            tableSecond.addCell(cell4);

            PdfPCell cell5 = new PdfPCell(new Phrase("Duration", tableFont));
            tableSecond.addCell(cell5);

            PdfPCell cell6 = new PdfPCell(new Phrase("Price", tableFont));
            tableSecond.addCell(cell6);

            tableFirst.addCell(new Phrase(cruise.getCruiseShip().getName(), tableFont));
            tableFirst.addCell(new Phrase(String.valueOf(cruise.getStart()), tableFont));
            tableFirst.addCell(new Phrase(String.valueOf(cruise.getEnd()), tableFont));
            tableSecond.addCell(new Phrase(String.valueOf(cruise.getRoute().getStartPort()), tableFont));
            tableSecond.addCell(new Phrase(String.valueOf(cruise.getRoute().getEndPort()), tableFont));
            tableSecond.addCell(new Phrase(String.valueOf(cruise.getTicketPrice()), tableFont));
            tableFirst.setSpacingAfter(25);
            tableSecond.setSpacingAfter(25);

            document.add(tableFirst);
            document.add(tableSecond);

            Paragraph footer = new Paragraph("© Dream Cruise 2023");
            footer.setAlignment(1);
            document.add(footer);

            document.close();

            openInBrowser(response, baos);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens ticket in browser
     *
     * @param response - HttpResponse
     * @param baos - ByteArrayOutputStream
     */
    private static void openInBrowser(HttpServletResponse response, ByteArrayOutputStream baos) {
        // setting some response headers
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        // setting the content type
        response.setContentType("application/pdf");
        // the content length
        response.setContentLength(baos.size());
        // write ByteArrayOutputStream to the ServletOutputStream
        OutputStream os;
        try {
            os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}