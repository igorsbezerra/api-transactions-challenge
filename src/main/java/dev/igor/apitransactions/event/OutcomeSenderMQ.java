package dev.igor.apitransactions.event;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class OutcomeSenderMQ {
    private final RabbitTemplate rabbitTemplate;
    private final Queue queueOutcome;

    public OutcomeSenderMQ(RabbitTemplate rabbitTemplate, Queue queue) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueOutcome = queue;
    }

    public void sendOutcome(String message) {
        log.info("*** OutcomeSenderMQ ***");
        rabbitTemplate.convertAndSend(queueOutcome.getName(), message);
    }
}
