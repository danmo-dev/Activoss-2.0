package com.datacenter.asset.infrastructure.adapters.external.pdfgenerator;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.Map;

@Component
public class FormatoActaFiller {

    public ResponseEntity<byte[]> generateActa(Map<String, String> datos) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            document.add(new Paragraph("ACTA DE ENTREGA"));
            document.add(new Paragraph("Nombre: " + datos.getOrDefault("nombre", "")));
            document.add(new Paragraph("Activo: " + datos.getOrDefault("activo", "")));
            document.add(new Paragraph("Fecha: " + datos.getOrDefault("fecha", "")));

            document.close();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=acta.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(baos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Error generando acta PDF", e);
        }
    }
}
