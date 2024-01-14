package dev.igor.apitransactions.event;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class OutcomeSenderMQ {
    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    public OutcomeSenderMQ(RabbitTemplate rabbitTemplate, Queue queue) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
    }

    public void sendOutcome(String message) {
        rabbitTemplate.convertAndSend(queue.getName(), message);
    }
}
