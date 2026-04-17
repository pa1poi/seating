package com.pavanseatin.demo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pavanseatin.demo.dto.AllocationRequest;
import com.pavanseatin.demo.entity.ExamHistory;
import com.pavanseatin.demo.repository.ExamHistoryRepository;
import com.pavanseatin.demo.service.PdfService;
import com.pavanseatin.demo.service.SeatingService;

@RestController
@RequestMapping("/api")   // ✅ IMPORTANT — SAME PREFIX AS MAIN
public class SeatingController {

    private final SeatingService service;
    private final PdfService pdfService;
    private final ExamHistoryRepository historyRepo;

    public SeatingController(
            SeatingService service,
            PdfService pdfService,
            ExamHistoryRepository historyRepo) {

        this.service = service;
        this.pdfService = pdfService;
        this.historyRepo = historyRepo;
    }

    @GetMapping("/")
    public String home() {
        return "Exam Seating System Running ✅";
    }

    // ================= ALLOCATE =================
    @PostMapping("/allocate-log")   // ✅ RENAMED to avoid conflict
    public String allocate(@RequestBody AllocationRequest request) {

        ExamHistory log = new ExamHistory();

        try {

            String result = service.allocateSeats(request);

            // log values
            log.setExamName("Exam");
            log.setExamType("Sessional");
            log.setSemester(String.valueOf(request.getSemester()));
            log.setCourse(request.getCourse());

            log.setStudentCount(0);
            log.setTeacherCount(0);
            log.setRoomCount(0);

            log.setStatus("SUCCESS");

            historyRepo.save(log);

            return result;

        } catch (Exception e) {

            e.printStackTrace();   // ✅ DEBUG

            log.setExamName("Exam");
            log.setStatus("FAILED");

            historyRepo.save(log);

            return "Allocation Failed ❌";
        }
    }

    // ================= TEACHER PDF =================
    @GetMapping("/pdf/teacher2")   // ✅ renamed (no clash)
    public ResponseEntity<byte[]> teacherPdf() throws Exception {

        byte[] pdf = pdfService.generateTeacherPdf();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=teacher.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    // ================= STUDENT PDF =================
    @GetMapping("/pdf/student2")   // ✅ renamed (no clash)
    public ResponseEntity<byte[]> studentPdf() throws Exception {

        byte[] pdf = pdfService.generateStudentPdf();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=student.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}