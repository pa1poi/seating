package com.pavanseatin.demo.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pavanseatin.demo.entity.Teacher;
import com.pavanseatin.demo.entity.TeacherAllocation;
import com.pavanseatin.demo.repository.TeacherAllocationRepository;
import com.pavanseatin.demo.repository.TeacherRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeacherMailService {

    private final TeacherRepository teacherRepo;
    private final TeacherAllocationRepository allocationRepo;
    private final PdfService pdfService;
    private final MailService mailService;

    public String sendTeacherMails() throws Exception {

        List<Teacher> teachers = teacherRepo.findAll();

        byte[] guidelines =
                Files.readAllBytes(
                        Path.of("src/main/resources/static/Teacher_Guidelines.pdf")
                );

        for (Teacher t : teachers) {

            byte[] seatingPdf =
                    pdfService.generateTeacherPdf();

            List<TeacherAllocation> allocList =
                    allocationRepo.findByTeacher_TeacherCode(
                            t.getTeacherCode()
                    );

            String roomNo = "Not Assigned";

            if (!allocList.isEmpty()) {
                roomNo = String.valueOf(
                        allocList.get(0).getRoomNo()
                );
            }

            String msg =
                    "Dear Faculty,\n\n"
                            + "Your invigilation duty has been assigned.\n\n"
                            + "Room Number : " + roomNo + "\n\n"
                            + "Please report 30 minutes before exam.\n"
                            + "Follow attached instructions carefully.\n\n"
                            + "Regards,\nExam Cell";

            mailService.sendMailWithBytes(
                    t.getEmail(),
                    "Invigilation Duty Allocation",
                    msg,
                    seatingPdf,
                    "Seating.pdf",
                    guidelines,
                    "Guidelines.pdf"
            );
        }

        return "Teacher mails sent successfully";
    }
}
