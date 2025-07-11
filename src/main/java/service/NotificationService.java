package service;

import dto.UserEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final EmailService emailService;

    @KafkaListener(topics = "user-events")
    public void handleUserEvent(UserEvent event) {
        String message = event.eventType() == UserEvent.EventType.CREATED
                ? "Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан."
                : "Здравствуйте! Ваш аккаунт был удалён.";

        emailService.sendEmail(event.email(), "Уведомление о вашем аккаунте", message);
    }
}