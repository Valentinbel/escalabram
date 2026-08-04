package com.escalabram.escalabram.utils.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${pinya.rabbitmq.exchange}")
    private String exchange;

    @Value("${pinya.rabbitmq.queue}")
    private String queue;

    @Value("${pinya.rabbitmq.dlq}")
    private String dlq;

    @Value("${pinya.rabbitmq.routing-key}")
    private String routingKey;

    // ---- Exchange principal ----
    @Bean
    public DirectExchange emailExchange() {
        return new DirectExchange(exchange);
    }

    // ---- Dead Letter Exchange (reçoit les messages en échec) ----
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(exchange + ".dlx");
    }

    // ---- Queue principale avec redirection vers DLX en cas d'échec ----
    @Bean
    public Queue emailQueue() {
        return QueueBuilder.durable(queue)
                .withArgument("x-dead-letter-exchange", exchange + ".dlx")
                .withArgument("x-dead-letter-routing-key", dlq)
                .build();
    }

    // ---- Dead Letter Queue (stocke les messages définitivement en échec) ----
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(dlq).build();
    }

    // ---- Bindings ----
    @Bean
    public Binding emailBinding() {
        return BindingBuilder.bind(emailQueue())
                .to(emailExchange())
                .with(routingKey);
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with(dlq);
    }

    // ---- Sérialisation JSON (au lieu du binaire Java par défaut) ----
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
