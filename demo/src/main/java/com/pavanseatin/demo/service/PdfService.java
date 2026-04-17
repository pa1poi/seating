package com.pavanseatin.demo.service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.pavanseatin.demo.entity.Seating;
import com.pavanseatin.demo.repository.SeatingRepository;

@Service
public class PdfService {

    private final SeatingRepository repo;

    public PdfService(SeatingRepository repo) {
        this.repo = repo;
    }

    /*
     =====================================================
        TEACHER PDF (ROOM WISE)
     =====================================================
     */
    public byte[] generateTeacherPdf() throws Exception {
        return buildRoomPdf(true);
    }

    /*
     =====================================================
        STUDENT MASTER PDF (ROOM WISE CLEAN VERSION)
     =====================================================
     */
    public byte[] generateStudentPdf() throws Exception {
        return buildRoomPdf(false);
    }

    /*
     =====================================================
        SINGLE STUDENT PDF (FOR EMAIL)
     =====================================================
     */
    public byte[] generateStudentPdf(Integer rollno) throws Exception {

        Seating seat = repo.findByRollno(rollno);

        if (seat == null) return null;

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfDocument pdf = new PdfDocument(new PdfWriter(out));
        Document doc = new Document(pdf);

        doc.add(new Paragraph("EXAM SEATING DETAILS")
                .setBold()
                .setFontSize(14)
                .setTextAlignment(TextAlignment.CENTER));

        doc.add(new Paragraph(" "));

        doc.add(new Paragraph("Roll Number : " + seat.getRollno()));
        doc.add(new Paragraph("Room Number : " + seat.getRoomNo()));
        doc.add(new Paragraph("Row Number : " + seat.getRowNo()));
        doc.add(new Paragraph("Bench Number : " + seat.getBenchNo()));
        doc.add(new Paragraph("Seat Number : " + seat.getSeatNo()));
        doc.add(new Paragraph("Side : " + seat.getSide()));

        doc.close();

        return out.toByteArray();
    }

    /*
     =====================================================
        CORE BUILDER (ROOM WISE)
     =====================================================
     */
    private byte[] buildRoomPdf(boolean teacherVersion) throws Exception {

        List<Seating> allSeats = repo.findAll();

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfDocument pdf = new PdfDocument(new PdfWriter(out));
        Document doc = new Document(pdf);

        doc.setMargins(18, 18, 18, 18);

        /*
           Group seats by room
         */
        Map<Integer, List<Seating>> roomMap = new LinkedHashMap<>();

        for (Seating s : allSeats) {

            roomMap.computeIfAbsent(s.getRoomNo(), k -> new java.util.ArrayList<>())
                    .add(s);
        }

        for (Integer roomNo : roomMap.keySet()) {

            List<Seating> seats = roomMap.get(roomNo);

            addHeader(doc);
            addInfoRow(doc, roomNo);

            if (teacherVersion) {
                addInvigilatorTable(doc);
            }

            addSeatingGrid(doc, seats);

            if (teacherVersion) {
                addTeacherFooter(doc, seats.size());
            }

            doc.add(new AreaBreak());
        }

        doc.close();
        return out.toByteArray();
    }

    /*
     =====================================================
        HEADER
     =====================================================
     */
    private void addHeader(Document doc) {

        Table header = new Table(1);
        header.setWidth(UnitValue.createPercentValue(100));

        header.addCell(
                new Cell()
                        .add(new Paragraph(
                                "GITAM School of Core Engineering & School of Computer Science Engineering\n" +
                                "Sessional Examination (Seating Arrangement)"
                        ).setBold()
                                .setFontSize(12)
                                .setTextAlignment(TextAlignment.CENTER))
                        .setBorder(new SolidBorder(2))
                        .setPadding(10)
        );

        doc.add(header);
    }

    /*
     =====================================================
        DATE | TIME | ROOM
     =====================================================
     */
    private void addInfoRow(Document doc, int roomNo) {

        Table info = new Table(3);
        info.setWidth(UnitValue.createPercentValue(100));

        info.addCell(infoCell("Date : " + LocalDate.now()));
        info.addCell(infoCell("Time : 9:30 – 11:00 AM"));
        info.addCell(infoCell("Room No : " + roomNo));

        doc.add(info);
    }

    /*
     =====================================================
        INVIGILATOR TABLE
     =====================================================
     */
    private void addInvigilatorTable(Document doc) {

        Table inv = new Table(new float[]{1, 4, 4});
        inv.setWidth(UnitValue.createPercentValue(100));

        inv.addCell(headerCell(""));
        inv.addCell(headerCell("Name(s) of Invigilator"));
        inv.addCell(headerCell("Signature"));

        inv.addCell(normalCell("1"));
        inv.addCell(normalCell(""));
        inv.addCell(normalCell(""));

        inv.addCell(normalCell("2"));
        inv.addCell(normalCell(""));
        inv.addCell(normalCell(""));

        doc.add(inv);
        doc.add(new Paragraph("\n"));
    }

    /*
     =====================================================
        SEATING GRID
     =====================================================
     */
    private void addSeatingGrid(Document doc, List<Seating> seats) {

        Table grid = new Table(8);
        grid.setWidth(UnitValue.createPercentValue(100));

        for (int i = 0; i < 4; i++) {
            grid.addCell(headerCell("Reg No"));
            grid.addCell(headerCell("0"));
        }

        int perRow = 4;

        for (int i = 0; i < seats.size(); i += perRow) {

            for (int j = 0; j < perRow; j++) {

                if (i + j < seats.size()) {

                    grid.addCell(normalCell(
                            String.valueOf(seats.get(i + j).getRollno())
                    ));

                    grid.addCell(normalCell("0"));

                } else {

                    grid.addCell(normalCell(""));
                    grid.addCell(normalCell(""));
                }
            }
        }

        doc.add(grid);
    }

    /*
     =====================================================
        FOOTER
     =====================================================
     */
    private void addTeacherFooter(Document doc, int total) {

        doc.add(new Paragraph("\n"));

        doc.add(new Paragraph("Invigilator Signature : _________________________________"));

        doc.add(new Paragraph(
                "Total Present : " + total + "          Total Absent : ________"
        ).setBold());
    }

    /*
     =====================================================
        STYLE HELPERS
     =====================================================
     */
    private Cell infoCell(String text) {
        return new Cell()
                .add(new Paragraph(text))
                .setBorder(new SolidBorder(1))
                .setPadding(6);
    }

    private Cell headerCell(String text) {
        return new Cell()
                .add(new Paragraph(text).setBold())
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setTextAlignment(TextAlignment.CENTER)
                .setBorder(new SolidBorder(1.5f))
                .setPadding(5);
    }

    private Cell normalCell(String text) {
        return new Cell()
                .add(new Paragraph(text))
                .setTextAlignment(TextAlignment.CENTER)
                .setBorder(new SolidBorder(1))
                .setPadding(5);
    }
}
