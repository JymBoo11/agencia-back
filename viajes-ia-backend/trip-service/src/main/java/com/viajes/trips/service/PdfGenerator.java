package com.viajes.trips.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.viajes.trips.model.Trip;

import java.io.ByteArrayOutputStream;

public class PdfGenerator {

    public static byte[] generateBudgetPdf(Trip trip) {
        try {
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);

            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

            document.add(new Paragraph("Presupuesto de Viaje", titleFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Título: " + trip.getTitle(), normalFont));
            document.add(new Paragraph("Estado: " + trip.getStatus(), normalFont));
            document.add(new Paragraph("Precio Total: €" + trip.getTotalPrice(), boldFont));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Desglose de Precios:", boldFont));
            document.add(new Paragraph(trip.getPriceBreakdownJson() != null ?
                    trip.getPriceBreakdownJson() : "No disponible", normalFont));

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Itinerario:", boldFont));
            document.add(new Paragraph(trip.getItineraryJson() != null ?
                    trip.getItineraryJson() : "No disponible", normalFont));

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }
    }
}