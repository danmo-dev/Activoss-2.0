package com.datacenter.asset.infrastructure.adapters.out.external.pdfgenerator;

import com.datacenter.asset.domain.models.assignment.AssetAssignment;
import com.datacenter.asset.domain.models.assignment.AssignmentActItem;
import com.datacenter.asset.domain.ports.out.external.PdfGeneratorPort;
import org.springframework.stereotype.Component;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class PdfGeneratorAdapter implements PdfGeneratorPort {

    private static final BaseColor BLUE_HEADER = new BaseColor(68, 114, 196);

    @Override
    public String generateAssignmentAct(
            AssetAssignment assignment,
            String personFirstName,
            String personLastName,
            String personDocumentNumber,
            String personEmail,
            String delivererFirstName,
            String delivererLastName,
            String delivererDocumentNumber,
            String delivererEmail,
            List<AssignmentActItem> assets,
            String companyTaxId,
            String companyName,
            String observaciones,
            String fechaHora,
            byte[] imagenObservacion) {

        String fileName = "acta_entrega_" + UUID.randomUUID() + ".pdf";
        String directoryPath = "src/main/resources/static/actas/";
        String filePath = directoryPath + fileName;
        String fechaActual = LocalDate.now().toString();

        // locationCode se toma del primer activo (cabecera)
        String locationCode = "";
        if (assets != null && !assets.isEmpty() && assets.get(0).getLocationCode() != null) {
            locationCode = assets.get(0).getLocationCode();
        }

        // Tipo de acuse dinámico
        String tipoAcuse = "Acuse de aceptado";
        if (assignment != null && assignment.getState() != null) {
            String stateName = assignment.getState().name();
            if ("REJECTED".equals(stateName)) tipoAcuse = "Acuse de rechazo";
            else if ("TRANSFERRED".equals(stateName)) tipoAcuse = "Acuse de transferencia";
            else if ("RETURNED".equals(stateName)) tipoAcuse = "Acuse de devolución";
        }

        try {
            File directory = new File(directoryPath);
            if (!directory.exists()) directory.mkdirs();

            Document document = new Document(PageSize.A4.rotate(), 20, 20, 20, 20);
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            document.add(createHeaderTable());
            document.add(createInfoTable(locationCode, companyTaxId, companyName, fechaActual));

            // BUCLE: construir filas a partir de la lista de activos
            List<String[]> listaActivos = new ArrayList<>();
            if (assets != null) {
                for (AssignmentActItem item : assets) {
                    String placaDefinitiva = (item.getAssetPlaca() != null && !item.getAssetPlaca().isEmpty())
                            ? item.getAssetPlaca()
                            : (item.getAssetCode() != null ? item.getAssetCode() : "");

                    listaActivos.add(new String[]{
                            placaDefinitiva,
                            item.getAssetName()     != null ? item.getAssetName()     : "",
                            item.getAssetSerial()   != null ? item.getAssetSerial()   : "",
                            item.getAssetModelo()   != null ? item.getAssetModelo()   : "",
                            item.getAssetMarca()    != null ? item.getAssetMarca()    : "",
                            item.getAssetAtributo() != null ? item.getAssetAtributo() : "",
                            item.getAssetEstado()   != null ? item.getAssetEstado()   : ""
                    });
                }
            }
            if (listaActivos.isEmpty()) {
                listaActivos.add(new String[]{"", "", "", "", "", "", ""});
            }

            document.add(createItemsTable(listaActivos));

            String receiverFullName = personFirstName + " " + personLastName;
            String delivererFullName = delivererFirstName + " " + delivererLastName;

            document.add(createFooterTable(
                    receiverFullName, personDocumentNumber, personEmail,
                    delivererFullName, delivererDocumentNumber, delivererEmail,
                    fechaActual, fechaHora, observaciones, imagenObservacion, tipoAcuse));

            document.close();
            return "/actas/" + fileName;
        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF del acta", e);
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
            logo.setAlignment(Element.ALIGN_CENTER);
            logoCell.addElement(logo);
        } catch (Exception e) {
            logoCell.setPhrase(new Phrase("LOGOS\nDataCenter / SIG",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
        }
        table.addCell(logoCell);

        table.addCell(createCell("ENTREGA DE ACTIVOS FIJOS", BaseColor.WHITE, BaseColor.BLACK, true, Element.ALIGN_CENTER, 12));
        table.addCell(createCell("Código: GAF-FM-01", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_LEFT, 9));

        table.addCell(createCell("Gestión Administrativa y Financiera", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 10));
        table.addCell(createCell("Versión: 4.0", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_LEFT, 9));

        table.addCell(createCell("", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));
        table.addCell(createCell("Página: 1 de 1", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_LEFT, 9));

        PdfPCell classCell = createCell(
                "Clasificación SGSI: (Confidencialidad: Uso Interno | Integridad: Crítica | Disponibilidad: Indispensable)\n" +
                "Este documento contiene información clasificada. Su modificación o divulgación está prohibida sin autorización del área responsable",
                BaseColor.WHITE, BaseColor.DARK_GRAY, false, Element.ALIGN_RIGHT, 7);
        classCell.setColspan(3);
        classCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(classCell);

        return table;
    }

    private PdfPTable createInfoTable(String locationCode, String companyTaxId, String companyName, String fechaActual) {
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);

        table.addCell(createCell("FECHA DE INGRESO DEL ACTIVO:", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_LEFT, 9));
        table.addCell(createCell(fechaActual, BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));

        PdfPCell origenH = createCell("ORIGEN CÓDIGO UBICACIÓN Y CENTRO DE COSTOS", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9);
        origenH.setColspan(4);
        table.addCell(origenH);

        PdfPCell empty1 = createCell("", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9);
        empty1.setColspan(2);
        table.addCell(empty1);
        PdfPCell ccLabel = createCell("Código Centro de Costos", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_LEFT, 9);
        ccLabel.setColspan(2);
        table.addCell(ccLabel);
        PdfPCell ccValue = createCell(companyTaxId, BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9);
        ccValue.setColspan(2);
        table.addCell(ccValue);

        PdfPCell novedadH = createCell("TIPO DE NOVEDAD", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9);
        novedadH.setColspan(2);
        table.addCell(novedadH);
        PdfPCell ubiLabel = createCell("Código Ubicación", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_LEFT, 9);
        ubiLabel.setColspan(2);
        table.addCell(ubiLabel);
        PdfPCell ubiValue = createCell(locationCode, BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9);
        ubiValue.setColspan(2);
        table.addCell(ubiValue);

        String[] tipos = {"Donación:", "Compra:", "Factura:", "Proveedor:", "Empresa:"};
        for (int i = 0; i < tipos.length; i++) {
            table.addCell(createCell(tipos[i], BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_LEFT, 9));
            String valorFila = tipos[i].equals("Empresa:") ? companyName : "";
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

    private PdfPTable createItemsTable(List<String[]> activos) {
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

        PdfPCell attrH = createCell("Descripción de atributos (marca, color, tamaño, serial, tipo de procesador, almacenamiento, entre otros)", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9);
        attrH.setColspan(5);
        table.addCell(attrH);

        table.addCell(createCell("SERIAL", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9));
        table.addCell(createCell("MODELO", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9));
        table.addCell(createCell("MARCA", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9));
        table.addCell(createCell("Atributo", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9));
        table.addCell(createCell("Estado", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9));

        for (String[] activo : activos) {
            for (int col = 0; col < 7; col++) {
                table.addCell(createCell(activo[col], BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));
            }
        }

        int filasVacias = Math.max(0, 4 - activos.size());
        for (int row = 0; row < filasVacias; row++) {
            for (int col = 0; col < 7; col++) {
                table.addCell(createCell("", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));
            }
        }
        return table;
    }

    private PdfPTable createFooterTable(
            String receiverName,
            String receiverCedula,
            String receiverEmail,
            String delivererName,
            String delivererCedula,
            String delivererEmail,
            String fecha,
            String fechaHora,
            String observaciones,
            byte[] imagenObservacion,
            String tipoAcuse) {

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);

        PdfPTable obsTable = new PdfPTable(1);
        obsTable.addCell(createCell("OBSERVACIONES", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9));

        PdfPCell obsContentCell = new PdfPCell();
        obsContentCell.setBackgroundColor(BaseColor.WHITE);
        obsContentCell.setPadding(6f);
        obsContentCell.setMinimumHeight(80f);

        String textoObs = (observaciones != null && !observaciones.trim().isEmpty())
                ? observaciones
                : "Sin observaciones adicionales.";
        Paragraph obsParagraph = new Paragraph(textoObs,
                FontFactory.getFont(FontFactory.HELVETICA, 9, BaseColor.BLACK));
        obsParagraph.setSpacingAfter(10f);
        obsContentCell.addElement(obsParagraph);

        if (imagenObservacion != null && imagenObservacion.length > 0) {
            try {
                Image img = Image.getInstance(imagenObservacion);
                img.scaleToFit(250, 150);
                img.setAlignment(Element.ALIGN_CENTER);
                obsContentCell.addElement(img);
            } catch (Exception e) {
                System.err.println("No se pudo insertar la imagen en el PDF: " + e.getMessage());
            }
        }

        obsTable.addCell(obsContentCell);

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
        String firmaRecibe = tipoAcuse + "\n" + fechaHora + "\n" + receiverEmail;
        firmTable.addCell(createCell(firmaRecibe, BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_LEFT, 8));

        firmTable.addCell(createCell("Nombre:", BaseColor.WHITE, BaseColor.BLACK, true, Element.ALIGN_LEFT, 9));
        firmTable.addCell(createCell(receiverName, BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));
        firmTable.addCell(createCell("Cedula:", BaseColor.WHITE, BaseColor.BLACK, true, Element.ALIGN_LEFT, 9));
        firmTable.addCell(createCell(receiverCedula, BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));
        firmTable.addCell(createCell("Cargo:", BaseColor.WHITE, BaseColor.BLACK, true, Element.ALIGN_LEFT, 9));
        firmTable.addCell(createCell("", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));

        PdfPCell entregaH = createCell("QUIEN ENTREGA EL ACTIVO", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9);
        entregaH.setColspan(2);
        firmTable.addCell(entregaH);

        firmTable.addCell(createCell("Firma:", BaseColor.WHITE, BaseColor.BLACK, true, Element.ALIGN_LEFT, 9));
        String firmaEntrega = "Acuse de entrega\n" + fechaHora + "\n" + delivererEmail;
        firmTable.addCell(createCell(firmaEntrega, BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_LEFT, 8));
        firmTable.addCell(createCell("Nombre:", BaseColor.WHITE, BaseColor.BLACK, true, Element.ALIGN_LEFT, 9));
        firmTable.addCell(createCell(delivererName, BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));
        firmTable.addCell(createCell("Cedula:", BaseColor.WHITE, BaseColor.BLACK, true, Element.ALIGN_LEFT, 9));
        firmTable.addCell(createCell(delivererCedula, BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));
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