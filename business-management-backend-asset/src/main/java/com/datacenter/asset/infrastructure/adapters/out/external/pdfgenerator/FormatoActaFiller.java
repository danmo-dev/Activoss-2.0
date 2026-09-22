package com.datacenter.asset.infrastructure.adapters.out.external.pdfgenerator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@SuppressWarnings("null")
@Component
public class FormatoActaFiller {

    private static final BaseColor BLUE_HEADER = new BaseColor(68, 114, 196);

    public ResponseEntity<byte[]> generateActa(Map<String, String> datos) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            Document document = new Document(PageSize.A4.rotate(), 20, 20, 20, 20);
            PdfWriter.getInstance(document, baos);
            document.open();

            String fecha = datos.getOrDefault("fecha", LocalDate.now().toString());
            String observaciones = datos.getOrDefault("observaciones", "");
            String empresa = datos.getOrDefault("empresa", ""); 
            
            String placa = datos.getOrDefault("placa", "N/A");
            String nombreActivo = datos.getOrDefault("nombreActivo", "N/A");
            String serial = datos.getOrDefault("serial", "");
            String modelo = datos.getOrDefault("modelo", "");
            String marca = datos.getOrDefault("marca", "");
            String atributo = datos.getOrDefault("atributo", "");
            String estado = datos.getOrDefault("estado", "");

            String recibeNombre = datos.getOrDefault("recibeNombre", "");
            String recibeCedula = datos.getOrDefault("recibeCedula", "");
            // <-- NUEVOS DATOS PARA LA FIRMA -->
            String recibeCorreo = datos.getOrDefault("recibeCorreo", "sin_correo@dominio.com");

            String entregaNombre = datos.getOrDefault("entregaNombre", "");
            String entregaCedula = datos.getOrDefault("entregaCedula", "");
            // <-- NUEVOS DATOS PARA LA FIRMA -->
            String entregaCorreo = datos.getOrDefault("entregaCorreo", "sin_correo@dominio.com");
            String fechaHora = datos.getOrDefault("fechaHora", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            document.add(createHeaderTable());
            document.add(createInfoTable(fecha, empresa)); 
            document.add(createItemsTable(placa, nombreActivo, serial, modelo, marca, atributo, estado));
            
            // Pasamos los nuevos datos al footer
            document.add(createFooterTable(observaciones, recibeNombre, recibeCedula, recibeCorreo, entregaNombre, entregaCedula, entregaCorreo, fecha, fechaHora));

            document.close();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=acta_entrega.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(baos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Error generando acta PDF", e);
        }
    }

    private PdfPTable createHeaderTable() {
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        try { table.setWidths(new float[]{1.5f, 3f, 1.2f}); } catch (Exception ignored) {}

        PdfPCell logoCell = new PdfPCell();
        logoCell.setRowspan(3);
        logoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        logoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        try {
            Image logo = Image.getInstance("src/main/resources/static/logo.png");
            logo.scaleToFit(120, 50);
            logoCell.addElement(logo);
        } catch (Exception e) {
            logoCell.setPhrase(new Phrase("LOGOS\nDataCenter / SIG", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
        }
        table.addCell(logoCell);

        table.addCell(createCell("ENTREGA DE ACTIVOS FIJOS", BaseColor.WHITE, BaseColor.BLACK, true, Element.ALIGN_CENTER, 12));
        table.addCell(createCell("Código: GAF-FM-01", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_LEFT, 9));

        table.addCell(createCell("Gestión Administrativa y Financiera", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 10));
        table.addCell(createCell("Versión: 4.0", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_LEFT, 9));

        table.addCell(createCell("", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9)); 
        table.addCell(createCell("Página: 1 de 1", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_LEFT, 9));

        PdfPCell classCell = createCell("Clasificación SGSI: (Confidencialidad: Uso Interno | Integridad: Crítica | Disponibilidad: Indispensable)\nEste documento contiene información clasificada. Su modificación o divulgación está prohibida sin autorización del área responsable", BaseColor.WHITE, BaseColor.DARK_GRAY, false, Element.ALIGN_RIGHT, 7);
        classCell.setColspan(3);
        classCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(classCell);

        return table;
    }

    private PdfPTable createInfoTable(String fecha, String empresa) {
        PdfPTable table = new PdfPTable(6); 
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);

        table.addCell(createCell("FECHA DE INGRESO DEL ACTIVO:", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_LEFT, 9));
        table.addCell(createCell(fecha, BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));
        PdfPCell origenH = createCell("ORIGEN CÓDIGO UBICACIÓN Y CENTRO DE COSTOS", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9);
        origenH.setColspan(4);
        table.addCell(origenH);

        PdfPCell empty1 = createCell("", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9);
        empty1.setColspan(2);
        table.addCell(empty1);
        PdfPCell ccLabel = createCell("Código Centro de Costos", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_LEFT, 9);
        ccLabel.setColspan(2);
        table.addCell(ccLabel);
        PdfPCell ccValue = createCell("", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9);
        ccValue.setColspan(2);
        table.addCell(ccValue);

        PdfPCell novedadH = createCell("TIPO DE NOVEDAD", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9);
        novedadH.setColspan(2);
        table.addCell(novedadH);
        PdfPCell ubiLabel = createCell("Código Ubicación", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_LEFT, 9);
        ubiLabel.setColspan(2);
        table.addCell(ubiLabel);
        PdfPCell ubiValue = createCell("1036", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9);
        ubiValue.setColspan(2);
        table.addCell(ubiValue);

        String[] tipos = {"Donación:", "Compra:", "Factura:", "Proveedor:", "Empresa:"};
        for (int i = 0; i < tipos.length; i++) {
            table.addCell(createCell(tipos[i], BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_LEFT, 9));
            String valorFila = tipos[i].equals("Empresa:") ? empresa : "";
            table.addCell(createCell(valorFila, BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));

            if (i == 0) {
                PdfPCell rightBox = createCell("", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9);
                rightBox.setColspan(4);
                rightBox.setRowspan(5); 
                table.addCell(rightBox);
            }
        }
        return table;
    }

    private PdfPTable createItemsTable(String placa, String nombre, String serial, String modelo, String marca, String atributo, String estado) {
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        try { table.setWidths(new float[]{1.2f, 2.5f, 1.2f, 1.2f, 1.2f, 1.5f, 1f}); } catch (Exception ignored) {}

        PdfPCell placaH = createCell("Placa N°", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9);
        placaH.setRowspan(2);
        table.addCell(placaH);

        PdfPCell nombreH = createCell("Nombre del Activo", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9);
        nombreH.setRowspan(2);
        table.addCell(nombreH);

        PdfPCell attrH = createCell("Descripción de atributos( marca,color, tamaño, serial, tipo de procesador, almacenamiento,entre otros)", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9);
        attrH.setColspan(5);
        table.addCell(attrH);

        table.addCell(createCell("SERIAL", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9));
        table.addCell(createCell("MODELO", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9));
        table.addCell(createCell("MARCA", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9));
        table.addCell(createCell("Atributo", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9));
        table.addCell(createCell("Estado", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9));

        table.addCell(createCell(placa, BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));
        table.addCell(createCell(nombre, BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));
        table.addCell(createCell(serial, BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));
        table.addCell(createCell(modelo, BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));
        table.addCell(createCell(marca, BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));
        table.addCell(createCell(atributo, BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));
        table.addCell(createCell(estado, BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 7; col++) {
                table.addCell(createCell("", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));
            }
        }
        return table;
    }

    private PdfPTable createFooterTable(String observaciones, String recibeNombre, String recibeCedula, String recibeCorreo, String entregaNombre, String entregaCedula, String entregaCorreo, String fecha, String fechaHora) {
        PdfPTable table = new PdfPTable(2); 
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);

        PdfPTable obsTable = new PdfPTable(1);
        obsTable.addCell(createCell("OBSERVACIONES", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9));
        String textoObs = (observaciones != null && !observaciones.trim().isEmpty()) ? observaciones + "\n\n\n\n\n\n\n\n" : "\n\n\n\n\n\n\n\n\n";
        obsTable.addCell(createCell(textoObs, BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_LEFT, 9));
        
        PdfPCell leftContainer = new PdfPCell(obsTable);
        leftContainer.setBorder(Rectangle.NO_BORDER);
        leftContainer.setPadding(0);
        leftContainer.setPaddingRight(5f);

        PdfPTable firmTable = new PdfPTable(2);
        try { firmTable.setWidths(new float[]{0.3f, 0.7f}); } catch (Exception ignored) {}

        PdfPCell respH = createCell("RESPONSABLE DEL ACTIVO", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9);
        respH.setColspan(2);
        firmTable.addCell(respH);

        firmTable.addCell(createCell("Firma:", BaseColor.WHITE, BaseColor.BLACK, true, Element.ALIGN_LEFT, 9));
        
        // <-- AQUI REEMPLAZAMOS EL ESPACIO EN BLANCO -->
        String firmaRecibe = "Acuse de aceptado\n" + fechaHora + "\n" + recibeCorreo;
        firmTable.addCell(createCell(firmaRecibe, BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_LEFT, 8));
        
        firmTable.addCell(createCell("Nombre:", BaseColor.WHITE, BaseColor.BLACK, true, Element.ALIGN_LEFT, 9));
        firmTable.addCell(createCell(recibeNombre, BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));
        firmTable.addCell(createCell("Cedula:", BaseColor.WHITE, BaseColor.BLACK, true, Element.ALIGN_LEFT, 9));
        firmTable.addCell(createCell(recibeCedula, BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));
        firmTable.addCell(createCell("Cargo:", BaseColor.WHITE, BaseColor.BLACK, true, Element.ALIGN_LEFT, 9));
        firmTable.addCell(createCell("", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));

        PdfPCell entregaH = createCell("QUIEN ENTREGA EL ACTIVO", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9);
        entregaH.setColspan(2);
        firmTable.addCell(entregaH);

        firmTable.addCell(createCell("Firma:", BaseColor.WHITE, BaseColor.BLACK, true, Element.ALIGN_LEFT, 9));
        
        // <-- AQUI REEMPLAZAMOS EL ESPACIO EN BLANCO -->
        String firmaEntrega = "Acuse de entrega\n" + fechaHora + "\n" + entregaCorreo;
        firmTable.addCell(createCell(firmaEntrega, BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_LEFT, 8));
        
        firmTable.addCell(createCell("Nombre:", BaseColor.WHITE, BaseColor.BLACK, true, Element.ALIGN_LEFT, 9));
        firmTable.addCell(createCell(entregaNombre, BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));
        firmTable.addCell(createCell("Cedula:", BaseColor.WHITE, BaseColor.BLACK, true, Element.ALIGN_LEFT, 9));
        firmTable.addCell(createCell(entregaCedula, BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));
        firmTable.addCell(createCell("Cargo:", BaseColor.WHITE, BaseColor.BLACK, true, Element.ALIGN_LEFT, 9));
        firmTable.addCell(createCell("", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 8));
        firmTable.addCell(createCell("Fecha:", BaseColor.WHITE, BaseColor.BLACK, true, Element.ALIGN_LEFT, 9));
        firmTable.addCell(createCell(fecha, BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));

        PdfPCell rightContainer = new PdfPCell(firmTable);
        rightContainer.setBorder(Rectangle.NO_BORDER);
        rightContainer.setPadding(0);
        rightContainer.setPaddingLeft(5f);

        table.addCell(leftContainer);
        table.addCell(rightContainer);

        return table;
    }

    private PdfPCell createCell(String text, BaseColor bgColor, BaseColor fgColor, boolean isBold, int alignment, float fontSize) {
        Font font = FontFactory.getFont(FontFactory.HELVETICA, fontSize, isBold ? Font.BOLD : Font.NORMAL, fgColor);
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(bgColor);
        cell.setHorizontalAlignment(alignment);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(6f);
        return cell;
    }
}