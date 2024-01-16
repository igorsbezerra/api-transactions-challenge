package dev.igor.apitransactions.api.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.igor.apitransactions.model.Transaction;

@ExtendWith(MockitoExtension.class)
public class TransactionResponseTest {
    @Test
    void getter_and_setter() {
        final var expectedId = "10";

        TransactionResponse dto = new TransactionResponse(expectedId);
        dto.setId(expectedId);

        Assertions.assertEquals(expectedId, dto.getId());
    }

    @Test
    void method_of() {
        Transaction transaction = Transaction.create();

        TransactionResponse response = TransactionResponse.of(transaction);

        Assertions.assertEquals(transaction.getId(), response.getId());
    }
}
