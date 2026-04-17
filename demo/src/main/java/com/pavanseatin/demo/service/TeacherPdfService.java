package com.pavanseatin.demo.service;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

@Service
public class TeacherPdfService {

    public byte[] generateTeacherPdf(String code) throws Exception {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document doc = new Document(pdf);

        doc.add(new Paragraph("INVIGILATOR DUTY DETAILS"));
        doc.add(new Paragraph("---------------------------"));

        doc.add(new Paragraph("Teacher Code : " + code));
        doc.add(new Paragraph("Room Allocated : Check System"));
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph("Please follow instructions attached."));

        doc.close();

        return out.toByteArray();
    }
}
