package com.escalabram.escalabram.email.listener;

import com.escalabram.escalabram.email.model.EmailMessage;
import com.escalabram.escalabram.email.publisher.EmailEventPublisher;
import com.escalabram.escalabram.event.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserRegisteredEventListener {
    // C'est la seule couche qui connaît les deux mondes : les événements métier ET l'infrastructure email.

    private final EmailEventPublisher emailEventPublisher;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onUserRegistered(UserRegisteredEvent event) {
        log.info("UserRegisteredEvent reçu pour {}, publication vers RabbitMQ", event.email());
        emailEventPublisher.publishEmailEvent(
                EmailMessage.builder()
                        .to(event.email())
                        //.subject("Bienvenue !") // TODO Thymeleaf
                        //.body("<h1>Bonjour " + event.name() + "</h1>")
                        .type(EmailMessage.EmailType.WELCOME)
                        .build()
        );
    }
}