package dev.igor.apitransactions.api.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;

import dev.igor.apitransactions.api.request.TransactionRequest;
import dev.igor.apitransactions.api.response.TransactionResponse;
import dev.igor.apitransactions.service.TransactionService;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {
    @Mock
    private TransactionService service;
    @InjectMocks
    private TransactionController controller;

    @Test
    void it_must_be_possible_to_create_a_transaction_when_invoking_the_createTransaction_method() throws JsonProcessingException {
        final var expectedId = "10";
        TransactionResponse response = new TransactionResponse(expectedId);
        response.setId(expectedId);
        final var expectedSourceAccount = "123456";
        final var expectedTargetAccount = "654321";
        final var expectedAmount = "100";
        TransactionRequest request = new TransactionRequest();
        request.setSourceAccount(expectedSourceAccount);
        request.setTargetAccount(expectedTargetAccount);
        request.setAmount(expectedAmount); 
        Mockito.when(service.createTransaction(Mockito.any())).thenReturn(response);

        ResponseEntity<TransactionResponse> result = controller.createTransaction(request);

        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
        Assertions.assertEquals(response.getId(), result.getBody().getId());
    }
}
