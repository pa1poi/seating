package com.pavanseatin.demo.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pavanseatin.demo.entity.Student;
import com.pavanseatin.demo.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentMailService {

    private final StudentRepository studentRepo;
    private final PdfService pdfService;
    private final MailService mailService;

    public String sendStudentMails() throws Exception {

        List<Student> students = studentRepo.findAll();

        byte[] instructions =
                Files.readAllBytes(
                        Path.of("src/main/resources/static/Instructions_Students.pdf")
                );

        for (Student s : students) {

            byte[] seatingPdf =
                    pdfService.generateStudentPdf(s.getRollno());

            String msg =
                    "Dear Student,\n\n"
                            + "Your exam seating arrangement is attached.\n"
                            + "Please follow instructions carefully.\n\n"
                            + "Best Wishes.";

            mailService.sendMailWithBytes(
                    s.getMailId(),
                    "Exam Seating Details",
                    msg,
                    seatingPdf,
                    "Seating.pdf",
                    instructions,
                    "Instructions.pdf"
            );
        }

        return "Student mails sent successfully";
    }

    // ================= TEST MAIL =================
public String sendTestMail() throws Exception {

    mailService.sendSimpleMail(
            "yourgmail@gmail.com",   // change to your email
            "Test Mail — Exam Seating System",
            """
            Hello 👋

            This is a test email from Exam Seating System.

            If you received this,
            Gmail configuration is SUCCESS.
            """
    );

    return "Test mail sent successfully";
}

}
