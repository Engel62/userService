package service;

import dto.UserEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import static dto.UserEvent.EventType.CREATED;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final EmailService emailService;

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "notification-group")
    public void handleUserEvent(UserEvent event) {
        Context context = new Context();
        String template, subject;

        if (event.eventType() == CREATED) {
            subject = "Аккаунт создан";
            template = "account-created";
            context.setVariable("message", "Ваш аккаунт на сайте ваш сайт был успешно создан");
        } else {
            subject = "Аккаунт удалён";
            template = "account-deleted";
            context.setVariable("message", "Ваш аккаунт был удалён");
        }

        emailService.sendEmail(event.email(), subject, template, context);
    }
}