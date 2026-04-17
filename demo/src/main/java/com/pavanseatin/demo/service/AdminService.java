package com.pavanseatin.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pavanseatin.demo.entity.Admin;
import com.pavanseatin.demo.repository.AdminRepository;

@Service
public class AdminService {

    private final AdminRepository repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Value("${app.master.key}")
    private String masterKey;

    public AdminService(AdminRepository repo) {
        this.repo = repo;
    }

    public boolean login(String email, String password, String key) {

        // Master key validation
        if (!masterKey.equals(key)) {
            return false;
        }

        Admin admin = repo.findByEmail(email).orElse(null);

        if (admin == null) {
            return false;
        }

        // BCrypt password check
        return encoder.matches(password, admin.getPassword());
    }
}
