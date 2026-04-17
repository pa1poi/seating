package com.pavanseatin.demo.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    // ================= SIMPLE MAIL =================
    public void sendSimpleMail(String to, String subject, String text)
            throws Exception {

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper =
                new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        mailSender.send(message);
    }

    // ================= MAIL WITH BYTES =================
    public void sendMailWithBytes(
            String to,
            String subject,
            String text,
            byte[] pdfBytes,
            String pdfName,
            byte[] instructionsBytes,
            String instructionsName
    ) throws Exception {

        MimeMessage message =
                mailSender.createMimeMessage();

        MimeMessageHelper helper =
                new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        // seating pdf
        helper.addAttachment(
                pdfName,
                new ByteArrayResource(pdfBytes)
        );

        // instructions pdf
        helper.addAttachment(
                instructionsName,
                new ByteArrayResource(instructionsBytes)
        );

        mailSender.send(message);
    }
}
