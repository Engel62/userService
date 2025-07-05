package controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.EmailService;
import org.thymeleaf.context.Context;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {
    private final EmailService emailService;

    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest request) {
        Context context = new Context();
        context.setVariable("message", request.message());

        emailService.sendEmail(
                request.email(),
                request.subject(),
                "custom-email",
                context
        );

        return ResponseEntity.ok("Email отправлен");
    }
}

public record EmailRequest(String email, String subject, String message) {}