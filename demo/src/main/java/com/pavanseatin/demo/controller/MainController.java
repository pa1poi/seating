package com.pavanseatin.demo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pavanseatin.demo.dto.AllocationRequest;
import com.pavanseatin.demo.service.ExcelService;
import com.pavanseatin.demo.service.PdfService;
import com.pavanseatin.demo.service.SeatingService;
import com.pavanseatin.demo.service.StudentMailService;
import com.pavanseatin.demo.service.TeacherAllocationPdfService;
import com.pavanseatin.demo.service.TeacherMailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MainController {

    private final ExcelService excelService;
    private final SeatingService seatingService;
    private final PdfService pdfService;
    private final StudentMailService studentMailService;
    private final TeacherMailService teacherMailService;
    private final TeacherAllocationPdfService teacherAllocationPdfService;

    // ================= UPLOAD =================

    @PostMapping("/upload/students")
    public String uploadStudents(@RequestParam("file") MultipartFile file) throws Exception {
        excelService.uploadStudents(file);
        return "Students uploaded successfully";
    }

    @PostMapping("/upload/rooms")
    public String uploadRooms(@RequestParam("file") MultipartFile file) throws Exception {
        excelService.uploadRooms(file);
        return "Rooms uploaded successfully";
    }

    @PostMapping("/upload/exam")
    public String uploadExam(@RequestParam("file") MultipartFile file) throws Exception {
        excelService.uploadExam(file);
        return "Exam uploaded successfully";
    }

    @PostMapping("/upload/teachers")
    public String uploadTeachers(@RequestParam("file") MultipartFile file) throws Exception {
        excelService.uploadTeachers(file);
        return "Teachers uploaded successfully";
    }

    // ================= ALLOCATE =================

    @PostMapping("/allocate")
    public String allocateSeats(@RequestBody AllocationRequest request) {
        return seatingService.allocateSeats(request);
    }

    // ================= PDF =================

    @GetMapping("/pdf/student")
    public ResponseEntity<byte[]> studentPdf() throws Exception {

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=student-seating.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfService.generateStudentPdf());
    }

    @GetMapping("/pdf/teacher")
    public ResponseEntity<byte[]> teacherPdf() throws Exception {

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=teacher-seating.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfService.generateTeacherPdf());
    }

    @GetMapping("/pdf/teacher-allocation")
    public ResponseEntity<byte[]> teacherAllocationPdf() throws Exception {

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=teacher-allocation.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(teacherAllocationPdfService.generateAllocationPdf());
    }

    // ================= EMAIL =================

    @GetMapping("/send/student-mails")
    public String sendStudentMails() throws Exception {
        return studentMailService.sendStudentMails();
    }

    @GetMapping("/send/teacher-mails")
    public String sendTeacherMails() throws Exception {
        return teacherMailService.sendTeacherMails();
    }

    // ================= TEST =================

    @GetMapping("/test-mail")
    public String testMail() throws Exception {
        return studentMailService.sendTestMail();
    }
}