package com.datacenter.asset.infrastructure.adapters.out.external.pdfgenerator;

import com.datacenter.asset.domain.models.assignment.AssetAssignment;
import com.datacenter.asset.domain.ports.out.external.PdfGeneratorPort;
import org.springframework.stereotype.Component;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.UUID;

@Component
public class PdfGeneratorAdapter implements PdfGeneratorPort {

    // Color Azul extraído del Excel
    private static final BaseColor BLUE_HEADER = new BaseColor(68, 114, 196);

    @Override
    public String generateAssignmentAct(
            AssetAssignment assignment,
            String personFirstName,
            String personLastName,
            String assetCode,
            String assetName) {

        String fileName = "acta_entrega_" + UUID.randomUUID() + ".pdf";
        String directoryPath = "src/main/resources/static/actas/";
        String filePath = directoryPath + fileName;

        try {
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Usamos formato horizontal (Landscape) por la cantidad de columnas
            Document document = new Document(PageSize.A4.rotate(), 20, 20, 20, 20);
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // --- 1. ENCABEZADO PRINCIPAL ---
            document.add(createHeaderTable());

            // --- 2. SECCIÓN DE INFORMACIÓN Y NOVEDAD ---
            document.add(createInfoTable());

            // --- 3. SECCIÓN DEL ACTIVO (TABLA PRINCIPAL) ---
            document.add(createItemsTable(assetCode, assetName));

            // --- 4. SECCIÓN DE OBSERVACIONES Y FIRMAS ---
            document.add(createFooterTable(personFirstName, personLastName));

            document.close();

            return "/actas/" + fileName;
        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF con formato Excel", e);
        }
    }

    private PdfPTable createHeaderTable() {
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        try { table.setWidths(new float[]{1.5f, 3f, 1.2f}); } catch (Exception ignored) {}

        // Celda del Logo
        PdfPCell logoCell = new PdfPCell();
        logoCell.setRowspan(3);
        logoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        logoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        // Intentar cargar logo (Ajusta la ruta real de tu logo)
        try {
            Image logo = Image.getInstance("src/main/resources/static/logo.png");
            logo.scaleToFit(120, 50);
            logoCell.addElement(logo);
        } catch (Exception e) {
            logoCell.setPhrase(new Phrase("LOGOS\nDataCenter / SIG", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
        }
        table.addCell(logoCell);

        // Fila 1
        table.addCell(createCell("ENTREGA DE ACTIVOS FIJOS", BaseColor.WHITE, BaseColor.BLACK, true, Element.ALIGN_CENTER, 12));
        table.addCell(createCell("Código: GAF-FM-01", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_LEFT, 9));

        // Fila 2
        table.addCell(createCell("Gestión Administrativa y Financiera", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 10));
        table.addCell(createCell("Versión: 4.0", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_LEFT, 9));

        // Fila 3
        table.addCell(createCell("", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9)); // Vacio en medio
        table.addCell(createCell("Página: 1 de 1", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_LEFT, 9));

        // Clasificación SGSI (Abarca toda la fila abajo)
        PdfPCell classCell = createCell("Clasificación SGSI: (Confidencialidad: Uso Interno | Integridad: Crítica | Disponibilidad: Indispensable)\nEste documento contiene información clasificada. Su modificación o divulgación está prohibida sin autorización del área responsable", BaseColor.WHITE, BaseColor.DARK_GRAY, false, Element.ALIGN_RIGHT, 7);
        classCell.setColspan(3);
        classCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(classCell);

        return table;
    }

    private PdfPTable createInfoTable() {
        PdfPTable table = new PdfPTable(6); // 6 columnas lógicas para maquetar
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);

        // Fila 1: Fecha y Origen
        table.addCell(createCell("FECHA DE INGRESO DEL ACTIVO:", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_LEFT, 9));
        table.addCell(createCell(LocalDate.now().toString(), BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));
        PdfPCell origenH = createCell("ORIGEN CÓDIGO UBICACIÓN Y CENTRO DE COSTOS", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9);
        origenH.setColspan(4);
        table.addCell(origenH);

        // Fila 2: Centro de costos
        PdfPCell empty1 = createCell("", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9);
        empty1.setColspan(2);
        table.addCell(empty1);
        PdfPCell ccLabel = createCell("Código Centro de Costos", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_LEFT, 9);
        ccLabel.setColspan(2);
        table.addCell(ccLabel);
        PdfPCell ccValue = createCell("", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9);
        ccValue.setColspan(2);
        table.addCell(ccValue);

        // Fila 3: Tipo de Novedad y Ubicación
        PdfPCell novedadH = createCell("TIPO DE NOVEDAD", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9);
        novedadH.setColspan(2);
        table.addCell(novedadH);
        PdfPCell ubiLabel = createCell("Código Ubicación", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_LEFT, 9);
        ubiLabel.setColspan(2);
        table.addCell(ubiLabel);
        PdfPCell ubiValue = createCell("1036", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9);
        ubiValue.setColspan(2);
        table.addCell(ubiValue);

        // Filas 4, 5, 6, 7: Tipos (Donación, Compra...)
        String[] tipos = {"Donación:", "Compra:", "Factura:", "Proveedor:"};
        for (int i = 0; i < tipos.length; i++) {
            table.addCell(createCell(tipos[i], BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_LEFT, 9));
            table.addCell(createCell("", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));
            if (i == 0) {
                // El cuadro gigante en blanco a la derecha
                PdfPCell rightBox = createCell("", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9);
                rightBox.setColspan(4);
                rightBox.setRowspan(4);
                table.addCell(rightBox);
            }
        }
        return table;
    }

    private PdfPTable createItemsTable(String assetCode, String assetName) {
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        try { table.setWidths(new float[]{1.2f, 2.5f, 1.2f, 1.2f, 1.2f, 1.5f, 1f}); } catch (Exception ignored) {}

        // Encabezados combinados
        PdfPCell placaH = createCell("Placa N°", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9);
        placaH.setRowspan(2);
        table.addCell(placaH);

        PdfPCell nombreH = createCell("Nombre del Activo", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9);
        nombreH.setRowspan(2);
        table.addCell(nombreH);

        PdfPCell attrH = createCell("Descripción de atributos( marca,color, tamaño, serial, tipo de procesador, almacenamiento,entre otros)", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9);
        attrH.setColspan(5);
        table.addCell(attrH);

        // Sub-Encabezados
        table.addCell(createCell("SERIAL", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9));
        table.addCell(createCell("MODELO", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9));
        table.addCell(createCell("MARCA", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9));
        table.addCell(createCell("Atributo", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9));
        table.addCell(createCell("Estado", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9));

        // Fila de datos (La real)
        table.addCell(createCell(assetCode, BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));
        table.addCell(createCell(assetName, BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));
        for (int i = 0; i < 5; i++) table.addCell(createCell("", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));

        // Filas vacías adicionales (para dar el efecto de tabla de formato impreso)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 7; col++) {
                table.addCell(createCell("", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));
            }
        }
        return table;
    }

    private PdfPTable createFooterTable(String personFirstName, String personLastName) {
        PdfPTable table = new PdfPTable(2); // Dividido 50% - 50%
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);

        // -- LADO IZQUIERDO: OBSERVACIONES --
        PdfPTable obsTable = new PdfPTable(1);
        obsTable.addCell(createCell("OBSERVACIONES", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9));
        obsTable.addCell(createCell("\n\n\n\n\n\n\n\n\n", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_LEFT, 9));
        
        PdfPCell leftContainer = new PdfPCell(obsTable);
        leftContainer.setBorder(Rectangle.NO_BORDER);
        leftContainer.setPadding(0);
        leftContainer.setPaddingRight(5f);

        // -- LADO DERECHO: FIRMAS --
        PdfPTable firmTable = new PdfPTable(2);
        try { firmTable.setWidths(new float[]{0.3f, 0.7f}); } catch (Exception ignored) {}

        // Responsable
        PdfPCell respH = createCell("RESPONSABLE DEL ACTIVO", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9);
        respH.setColspan(2);
        firmTable.addCell(respH);

        firmTable.addCell(createCell("Firma:", BaseColor.WHITE, BaseColor.BLACK, true, Element.ALIGN_LEFT, 9));
        firmTable.addCell(createCell("\n\n", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_LEFT, 9));
        firmTable.addCell(createCell("Nombre:", BaseColor.WHITE, BaseColor.BLACK, true, Element.ALIGN_LEFT, 9));
        firmTable.addCell(createCell(personFirstName + " " + personLastName, BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));
        firmTable.addCell(createCell("Cedula:", BaseColor.WHITE, BaseColor.BLACK, true, Element.ALIGN_LEFT, 9));
        firmTable.addCell(createCell("", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));
        firmTable.addCell(createCell("Cargo:", BaseColor.WHITE, BaseColor.BLACK, true, Element.ALIGN_LEFT, 9));
        firmTable.addCell(createCell("", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));

        // Quien Entrega
        PdfPCell entregaH = createCell("QUIEN ENTREGA EL ACTIVO", BLUE_HEADER, BaseColor.WHITE, true, Element.ALIGN_CENTER, 9);
        entregaH.setColspan(2);
        firmTable.addCell(entregaH);

        firmTable.addCell(createCell("Firma:", BaseColor.WHITE, BaseColor.BLACK, true, Element.ALIGN_LEFT, 9));
        firmTable.addCell(createCell("\n\n", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_LEFT, 9));
        firmTable.addCell(createCell("Nombre:", BaseColor.WHITE, BaseColor.BLACK, true, Element.ALIGN_LEFT, 9));
        firmTable.addCell(createCell("NIÑO CASTAÑEDA LEIDY NATALIA", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));
        firmTable.addCell(createCell("Cedula:", BaseColor.WHITE, BaseColor.BLACK, true, Element.ALIGN_LEFT, 9));
        firmTable.addCell(createCell("1.070.975.173", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));
        firmTable.addCell(createCell("Cargo:", BaseColor.WHITE, BaseColor.BLACK, true, Element.ALIGN_LEFT, 9));
        firmTable.addCell(createCell("DIRECTORA ADMINISTRATIVA, FINANCIERA Y DE TH", BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 8));
        firmTable.addCell(createCell("Fecha:", BaseColor.WHITE, BaseColor.BLACK, true, Element.ALIGN_LEFT, 9));
        firmTable.addCell(createCell(LocalDate.now().toString(), BaseColor.WHITE, BaseColor.BLACK, false, Element.ALIGN_CENTER, 9));

        PdfPCell rightContainer = new PdfPCell(firmTable);
        rightContainer.setBorder(Rectangle.NO_BORDER);
        rightContainer.setPadding(0);
        rightContainer.setPaddingLeft(5f);

        table.addCell(leftContainer);
        table.addCell(rightContainer);

        return table;
    }

    // Helper metod para crear celdas de forma limpia
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