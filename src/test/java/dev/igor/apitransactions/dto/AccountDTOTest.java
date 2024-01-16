package dev.igor.apitransactions.dto;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountDTOTest {
    @Test
    void getter_and_setter() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedAccountCode = "123456";
        final var expectedUserId = "1";

        AccountDTO dto = new AccountDTO();
        dto.setId(expectedId);
        dto.setAccountCode(expectedAccountCode);
        dto.setUserId(expectedUserId);

        Assertions.assertEquals(expectedId, dto.getId());
        Assertions.assertEquals(expectedAccountCode, dto.getAccountCode());
        Assertions.assertEquals(expectedUserId, dto.getUserId());
    }
}
