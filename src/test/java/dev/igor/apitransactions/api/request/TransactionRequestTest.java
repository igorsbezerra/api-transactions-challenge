package dev.igor.apitransactions.api.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TransactionRequestTest {
    @Test
    void getter_and_setter() {
        final var expectedSourceAccount = "123456";
        final var expectedTargetAccount = "654321";
        final var expectedAmount = "100";

        TransactionRequest request = new TransactionRequest();
        request.setSourceAccount(expectedSourceAccount);
        request.setTargetAccount(expectedTargetAccount);
        request.setAmount(expectedAmount); 

        Assertions.assertEquals(expectedSourceAccount, request.getSourceAccount());    
        Assertions.assertEquals(expectedTargetAccount, request.getTargetAccount());    
        Assertions.assertEquals(expectedAmount, request.getAmount());    
    }
}
