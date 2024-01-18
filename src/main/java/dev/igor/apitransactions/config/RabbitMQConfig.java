package dev.igor.apitransactions.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitMQConfig {
    @Bean
    @Primary
    public Queue queueOutcome() {
        // This queue has the following properties:
        // name: my_durable
        // durable: true
        // exclusive: false
        // auto_delete: false
        return new Queue("queue-outcome", true, false, false);
    }

    @Bean
    public Queue queueIncome() {
        // This queue has the following properties:
        // name: my_durable
        // durable: true
        // exclusive: false
        // auto_delete: false
        return new Queue("queue-income", true, false, false);
    }

    @Bean
    public Queue queueDevolution() {
        // This queue has the following properties:
        // name: my_durable
        // durable: true
        // exclusive: false
        // auto_delete: false
        return new Queue("queue-devolution", true, false, false);
    }
}
