package dev.igor.apitransactions;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockserver.client.MockServerClient;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.utility.DockerImageName;

public class MockServerAPIContainer implements BeforeAllCallback {

    private static boolean containerStarted = false;
    private static MockServerContainer mockServer = new MockServerContainer(DockerImageName.parse("mockserver/mockserver:5.15.0"))
        .withExposedPorts(1080);
    public static MockServerClient mockServerClient;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        if (!containerStarted) {
            mockServer.start();
            mockServerClient = new MockServerClient(
                mockServer.getHost(),
                mockServer.getServerPort()
            );

            String url = "http://" + mockServer.getHost() + ":" + mockServer.getMappedPort(1080);
            System.setProperty("services.account.url", url);
            containerStarted = true;
        }
    }
}
