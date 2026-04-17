package com.pavanseatin.demo.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin("*")
public class InstructionController {

    private static final String DIR = "D:/exam_uploads/";

    private void saveFile(String name, MultipartFile file) throws Exception {

        File folder = new File(DIR);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        Path path = Paths.get(DIR + name);

        Files.write(path, file.getBytes());
    }

    @PostMapping("/student-instructions")
    public ResponseEntity<?> uploadStudent(
            @RequestParam("file") MultipartFile file
    ) throws Exception {

        saveFile("Instructions_Students.pdf", file);

        return ResponseEntity.ok("Student uploaded");
    }

    @PostMapping("/teacher-instructions")
    public ResponseEntity<?> uploadTeacher(
            @RequestParam("file") MultipartFile file
    ) throws Exception {

        saveFile("Teacher_Guidelines.pdf", file);

        return ResponseEntity.ok("Teacher uploaded");
    }
}
