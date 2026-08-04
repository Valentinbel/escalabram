package com.escalabram.escalabram.email.publisher;

import com.escalabram.escalabram.email.model.EmailMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    @Value("${pinya.rabbitmq.exchange}")
    private String exchange;

    @Value("${pinya.rabbitmq.routing-key}")
    private String routingKey;

    public void publishEmailEvent(EmailMessage emailMessage) {
        // Enrichissement du message
        emailMessage.setCreatedAt(LocalDateTime.now());
        emailMessage.setCorrelationId(UUID.randomUUID().toString());

        log.info("Publishing email event [correlationId={}] to={}",
                emailMessage.getCorrelationId(), emailMessage.getTo());

        rabbitTemplate.convertAndSend(exchange, routingKey, emailMessage);
    }
}
