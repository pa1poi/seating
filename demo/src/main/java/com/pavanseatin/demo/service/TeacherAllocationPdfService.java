package com.pavanseatin.demo.service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.pavanseatin.demo.entity.TeacherAllocation;
import com.pavanseatin.demo.repository.TeacherAllocationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeacherAllocationPdfService {

    private final TeacherAllocationRepository allocationRepo;

    public byte[] generateAllocationPdf() throws Exception {

        List<TeacherAllocation> list = allocationRepo.findAll();

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document doc = new Document(pdf);

        doc.add(new Paragraph("Teacher Invigilation Allocation")
                .setBold()
                .setFontSize(16));

        doc.add(new Paragraph("Date: " + LocalDate.now()));
        doc.add(new Paragraph("\n"));

        Table table = new Table(4);

        table.addHeaderCell("Teacher Code");
        table.addHeaderCell("Teacher Name");
        table.addHeaderCell("Room No");
        table.addHeaderCell("Allocation ID");

        for (TeacherAllocation ta : list) {

            table.addCell(
                    new Cell().add(
                            new Paragraph(
                                    ta.getTeacher().getTeacherCode()
                            )
                    )
            );

            table.addCell(
                    new Cell().add(
                            new Paragraph(
                                    ta.getTeacher().getName()
                            )
                    )
            );

            table.addCell(
                    new Cell().add(
                            new Paragraph(
                                    String.valueOf(ta.getRoomNo())
                            )
                    )
            );

            table.addCell(
                    new Cell().add(
                            new Paragraph(
                                    String.valueOf(ta.getId())
                            )
                    )
            );
        }

        doc.add(table);

        doc.close();

        return out.toByteArray();
    }
}
