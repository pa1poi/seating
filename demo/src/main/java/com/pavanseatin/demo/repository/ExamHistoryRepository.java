package com.pavanseatin.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pavanseatin.demo.entity.ExamHistory;

public interface ExamHistoryRepository
        extends JpaRepository<ExamHistory, Long> {
}
