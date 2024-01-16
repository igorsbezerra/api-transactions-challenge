package dev.igor.apitransactions.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class NotificationDTOTest {
    @Test
    void getter_and_setter() {
        final var expectedMessage = true;
        NotificationDTO dto = new NotificationDTO();

        dto.setMessageSent(expectedMessage);

        Assertions.assertEquals(expectedMessage, dto.isMessageSent());
    }
}
