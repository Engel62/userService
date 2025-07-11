package controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import service.EmailService;


@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final EmailService emailService;

    @PostMapping("/email")
    public void sendEmailNotification(
            @RequestParam String email,
            @RequestParam String subject,
            @RequestParam String message) {
        emailService.sendEmail(email, subject, message);
    }
}