package com.pavanseatin.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pavanseatin.demo.entity.ExamHistory;
import com.pavanseatin.demo.repository.ExamHistoryRepository;

@RestController
@RequestMapping("/api/history")
@CrossOrigin("*")
public class HistoryController {

    private final ExamHistoryRepository repo;

    public HistoryController(ExamHistoryRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/save")
    public ExamHistory save(@RequestBody ExamHistory history) {
        return repo.save(history);
    }

    @GetMapping("/all")
    public List<ExamHistory> getAll() {
        return repo.findAll();
    }
}
