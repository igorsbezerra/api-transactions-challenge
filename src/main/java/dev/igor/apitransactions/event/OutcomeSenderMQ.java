package dev.igor.apitransactions.event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import dev.igor.apitransactions.config.RabbitMQConfig;

@Component
public class OutcomeSenderMQ {
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConfig rabbitMQConfig;

    public OutcomeSenderMQ(RabbitTemplate rabbitTemplate, RabbitMQConfig rabbitMQConfig) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQConfig = rabbitMQConfig;
    }

    public void sendOutcome(String message) {
        rabbitTemplate.convertAndSend(rabbitMQConfig.queueOutcome().getName(), message);
    }
}
