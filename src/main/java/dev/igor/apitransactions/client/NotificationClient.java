package dev.igor.apitransactions.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.igor.apitransactions.dto.NotificationDTO;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class NotificationClient {

    private String uri = "https://run.mocky.io/v3/9769bf3a-b0b6-477a-9ff5-91f63010c9d3";

    public void sent() {
        HttpClient http = HttpClient.newHttpClient();
        
        for (int i = 1; i <= 3; i++) {
            try {
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).GET().build();
                HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    NotificationDTO notificationResponse = objectMapper.readValue(response.body(), NotificationDTO.class);
                    if (notificationResponse.isMessageSent()) {
                        return;
                    }
                }
            } catch (IOException | InterruptedException e) {
                log.error("Failed to communicate with notification service");
            }

            try {
                Thread.sleep(60000); // 1 min
            } catch (InterruptedException e) {
                log.error("Failed to use sleep");
            }
        }
    }   
}
