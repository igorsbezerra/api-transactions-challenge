package dev.igor.apitransactions.event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import dev.igor.apitransactions.config.RabbitMQConfig;

@Component
public class IncomeSenderMQ {
private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConfig rabbitMQConfig;

    public IncomeSenderMQ(RabbitTemplate rabbitTemplate, RabbitMQConfig rabbitMQConfig) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQConfig = rabbitMQConfig;
    }

    public void sendIncome(String message) {
        rabbitTemplate.convertAndSend(rabbitMQConfig.queueIncome().getName(), message);
    }
}
