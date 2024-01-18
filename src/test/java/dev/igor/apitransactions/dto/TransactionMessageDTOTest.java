package dev.igor.apitransactions.dto;

import dev.igor.apitransactions.api.request.TransactionRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TransactionMessageDTOTest {
    @Test
    void getter_and_setter() {
        final var expectedSourceAccount = "123456";
        final var expectedTargetAccount = "654321";
        final var expectedAmount = "100";

        TransactionMessageDTO request = new TransactionMessageDTO();
        request.setSourceAccount(expectedSourceAccount);
        request.setTargetAccount(expectedTargetAccount);
        request.setAmount(expectedAmount);

        Assertions.assertEquals(expectedSourceAccount, request.getSourceAccount());
        Assertions.assertEquals(expectedTargetAccount, request.getTargetAccount());
        Assertions.assertEquals(expectedAmount, request.getAmount());

        TransactionMessageDTO request2 = new TransactionMessageDTO(expectedSourceAccount, expectedTargetAccount, expectedAmount);

        Assertions.assertEquals(expectedSourceAccount, request2.getSourceAccount());
        Assertions.assertEquals(expectedTargetAccount, request2.getTargetAccount());
        Assertions.assertEquals(expectedAmount, request2.getAmount());
    }
}
