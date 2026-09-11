package com.datacenter.asset.infrastructure.adapters.external.pdfgenerator;

import com.datacenter.asset.domain.assignment.AssetAssignment;
import com.datacenter.asset.domain.ports.external.out.PdfGeneratorPort;
import org.springframework.stereotype.Component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.UUID;

@Component
public class PdfGeneratorAdapter implements PdfGeneratorPort {

    @Override
    public String generateAssignmentAct(
        AssetAssignment assignment,
        String personFirstName,
        String personLastName,
        String assetCode,
        String assetName) {
            
        String fileName = "acta_entrega_" + UUID.randomUUID() + ".pdf";
        String filePath = "src/main/resources/static/actas/" + fileName;

        try {
            File directory = new File("src/main/resources/static/actas/");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Fuentes
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.BLACK);
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.BLACK);
            Font smallItalicFont = new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC, BaseColor.DARK_GRAY);

            // Términos de Confidencialidad
            Paragraph confidentialityTitle = new Paragraph("TERMINOS DE CONFIDENCIALIDAD", new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.DARK_GRAY));
            confidentialityTitle.setAlignment(Element.ALIGN_JUSTIFIED);
            document.add(confidentialityTitle);
            
            Paragraph confidentialityBody = new Paragraph(
                    "Este documento es resultado del trabajo desarrollado por el área de tecnología de DATACENTER COLOMBIA S.A.S y para uso exclusivo de DATACENTER COLOMBIA S.A.S. " +
                    "Por razones de Confidencialidad de la información, las ideas, conceptos, definiciones, aplicaciones, planes de trabajo y en general las soluciones contenidas " +
                    "en esta línea base de seguridad de la información, no debe ser revelado, usado, duplicado o publicado total o parcialmente, fuera de la compañía u organización, " +
                    "sin una autorización expresa escrita de DATACENTER COLOMBIA S.A.S.", smallItalicFont);
            confidentialityBody.setAlignment(Element.ALIGN_JUSTIFIED);
            confidentialityBody.setSpacingAfter(20);
            document.add(confidentialityBody);

            // Título
            Paragraph title = new Paragraph("ACTA DE ENTREGA DE ACTIVO TECNOLÓGICO", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(10);
            document.add(title);

            Paragraph subtitle = new Paragraph("Fecha de Generación: " + LocalDate.now().toString(), normalFont);
            subtitle.setAlignment(Element.ALIGN_RIGHT);
            subtitle.setSpacingAfter(20);
            document.add(subtitle);

            // Tabla de Datos
            PdfPTable detailsTable = new PdfPTable(2);
            detailsTable.setWidthPercentage(100);
            detailsTable.setSpacingBefore(10f);
            detailsTable.setSpacingAfter(20f);

            PdfPCell headerCell = new PdfPCell();
            headerCell.setBackgroundColor(new BaseColor(41, 128, 185));
            headerCell.setPadding(8);
            headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

            headerCell.setPhrase(new Phrase("Atributo", headerFont));
            detailsTable.addCell(headerCell);
            headerCell.setPhrase(new Phrase("Detalle", headerFont));
            detailsTable.addCell(headerCell);

            // Inyectamos los datos
            detailsTable.addCell(createCell("ID de Asignación", normalFont));
            detailsTable.addCell(createCell(assignment.getId().toString(), normalFont));
            
            detailsTable.addCell(createCell("Código del Activo", normalFont));
            detailsTable.addCell(createCell(assetCode, normalFont));
            
            detailsTable.addCell(createCell("Nombre del Activo", normalFont));
            detailsTable.addCell(createCell(assetName, normalFont));
            
            detailsTable.addCell(createCell("Nombre del Usuario", normalFont));
            detailsTable.addCell(createCell(personFirstName + " " + personLastName, normalFont));

            document.add(detailsTable);

            // Declaración
            Paragraph declaration = new Paragraph(
                    "PROPÓSITO Y ALCANCE: El objetivo de este documento es establecer la entrega formal del activo tecnológico mencionado anteriormente. " +
                    "El usuario asume la responsabilidad del cuidado, uso adecuado y salvaguarda de la información contenida en el mismo.", normalFont);
            declaration.setAlignment(Element.ALIGN_JUSTIFIED);
            declaration.setSpacingAfter(40);
            document.add(declaration);

            // Firmas
            PdfPTable signaturesTable = new PdfPTable(3);
            signaturesTable.setWidthPercentage(100);
            
            PdfPCell sigCell = new PdfPCell();
            sigCell.setBorder(PdfPCell.NO_BORDER);
            sigCell.setHorizontalAlignment(Element.ALIGN_CENTER);

            sigCell.setPhrase(new Phrase("__________________________\nElaborado Por:\n[Área de TI]\nDATACENTER COLOMBIA S.A.S", normalFont));
            signaturesTable.addCell(sigCell);

            sigCell.setPhrase(new Phrase("__________________________\nRevisado Por:\n[Coordinador]\nDATACENTER COLOMBIA S.A.S", normalFont));
            signaturesTable.addCell(sigCell);

            // Usamos nombre y apellido concatenados
            String fullPersonName = personFirstName + " " + personLastName;
            sigCell.setPhrase(new Phrase("__________________________\nAprobado / Recibido Por:\n" + fullPersonName + "\n", normalFont));
            signaturesTable.addCell(sigCell);

            document.add(signaturesTable);
            document.close();

            return "/actas/" + fileName;
        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF", e);
        }
    }

    private PdfPCell createCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(8);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }
}
