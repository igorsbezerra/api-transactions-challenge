package dev.igor.apitransactions;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

public class RabbitMQContainer implements BeforeAllCallback {

    private static boolean containerStarted = false;
    private static GenericContainer rabbitmq = new GenericContainer<>(DockerImageName.parse("rabbitmq:3.7.25-management-alpine"))
        .withExposedPorts(5672, 15672)
        .withEnv("RABBITMQ_DEFAULT_USER", "guest")
        .withEnv("RABBITMQ_DEFAULT_PASS", "guest");

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        if (!containerStarted) {
            rabbitmq.start();
            System.setProperty("spring.rabbitmq.port", rabbitmq.getMappedPort(5672).toString());
            containerStarted = true;
        }
    }
}
