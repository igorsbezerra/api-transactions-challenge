package dev.igor.apitransactions.event.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import dev.igor.apitransactions.config.RabbitMQConfig;
import dev.igor.apitransactions.event.SenderMQ;

@Component
public class SenderMQImpl implements SenderMQ {
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQConfig rabbitMQConfig;

    public SenderMQImpl(RabbitTemplate rabbitTemplate, RabbitMQConfig rabbitMQConfig) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQConfig = rabbitMQConfig;
    }

    @Override
    public void sendIncome(String message) {
        rabbitTemplate.convertAndSend(rabbitMQConfig.queueIncome().getName(), message);
    }

    @Override
    public void sendOutCome(String message) {
        rabbitTemplate.convertAndSend(rabbitMQConfig.queueOutcome().getName(), message);
    }

    @Override
    public void sendDevolution(String message) {
        rabbitTemplate.convertAndSend(rabbitMQConfig.queueDevolution().getName(), message);
    }
}
