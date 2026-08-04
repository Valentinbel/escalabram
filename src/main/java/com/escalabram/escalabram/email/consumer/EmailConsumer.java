package com.escalabram.escalabram.email.consumer;

import com.escalabram.escalabram.email.model.EmailMessage;
import com.escalabram.escalabram.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailConsumer {
    private final EmailService emailService;

    @RabbitListener(queues = "${pinya.rabbitmq.queue}")
    public void consume(EmailMessage message) {
        log.info("Consuming email event [correlationId={}] type={} to={}",
                message.getCorrelationId(), message.getType(), message.getTo());

        try {
            emailService.send(message);
            log.info("Email sent successfully [correlationId={}]", message.getCorrelationId());

        } catch (MailException e) {
            // Spring retry va automatiquement retenter selon la config
            // Après épuisement des tentatives → message part en DLQ
            log.error("Failed to send email [correlationId={}] : {}",
                    message.getCorrelationId(), e.getMessage());
            throw e; // Relancer pour déclencher le retry
        }
    }

    // ---- Consumer de la Dead Letter Queue (alerting, monitoring) ----
    @RabbitListener(queues = "${pinya.rabbitmq.dlq}")
    public void consumeDeadLetter(EmailMessage message) {
        log.error("Email definitively failed [correlationId={}] to={} type={}",
                message.getCorrelationId(), message.getTo(), message.getType());

        // Ici : alerter l'équipe (Slack, PagerDuty...), stocker en BDD, etc.
    }
}
