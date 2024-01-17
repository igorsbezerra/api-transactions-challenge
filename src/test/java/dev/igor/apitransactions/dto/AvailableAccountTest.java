package dev.igor.apitransactions.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AvailableAccountTest {
    @Test
    void getter_and_setter() {
        AvailableAccount dto = new AvailableAccount();
        dto.setAvailable("true");

        Assertions.assertTrue(Boolean.valueOf(dto.getAvailable()));
    }
}
