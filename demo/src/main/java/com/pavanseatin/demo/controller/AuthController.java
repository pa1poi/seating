package com.pavanseatin.demo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pavanseatin.demo.service.AdminService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final AdminService service;

    public AuthController(AdminService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public boolean login(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String key
    ) {
        return service.login(email, password, key);
    }
}
