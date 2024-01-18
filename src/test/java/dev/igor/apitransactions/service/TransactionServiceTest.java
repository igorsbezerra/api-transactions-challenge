package dev.igor.apitransactions.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dev.igor.apitransactions.api.response.RefoundResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonProcessingException;

import dev.igor.apitransactions.api.request.TransactionRequest;
import dev.igor.apitransactions.api.response.TransactionResponse;
import dev.igor.apitransactions.client.AccountClient;
import dev.igor.apitransactions.dto.AccountDTO;
import dev.igor.apitransactions.error.ActionsYourSelfException;
import dev.igor.apitransactions.model.Transaction;
import dev.igor.apitransactions.model.TransactionItem;
import dev.igor.apitransactions.model.enums.TypeTransaction;
import dev.igor.apitransactions.repository.TransactionRepository;
import dev.igor.apitransactions.service.command.TransactionCommandHandler;
import dev.igor.apitransactions.service.impl.TransactionServiceImpl;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    private TransactionRepository repository;
    @Mock
    private AccountClient accountClient;
    @Mock
    private TransactionCommandHandler commandHandler;
    @InjectMocks
    private TransactionServiceImpl service;

    @Test
    void should_not_create_a_transaction_when_the_accounts_are_the_same() {
        TransactionRequest invalidRequest = createInvalidTransactionRequest();
        Assertions.assertThrows(ActionsYourSelfException.class, () -> service.createTransaction(invalidRequest));
    }

    @Test
    void test2() throws JsonProcessingException {
        AccountDTO accountDTO = createAccountDTO();
        TransactionRequest request = createValidTransactionRequest();
        TransactionItem transactionItem = createTransactionItem();
        Transaction transaction = createTransaction();
        Mockito.when(accountClient.findByAccountCode(Mockito.anyString())).thenReturn(accountDTO);
        Mockito.when(commandHandler.handler(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(transactionItem);
        Mockito.when(repository.save(Mockito.any())).thenReturn(transaction);

        TransactionResponse result = service.createTransaction(request);

        Assertions.assertNotNull(result);
    }

    @Test
    void test3() throws JsonProcessingException {
        final var expectedId = "1";
        final var expectedResponse = "true";
        final var expectedTransaction = createValidTransactionRequest();

        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(expectedTransaction));

        RefoundResponse refoundResponse = service.refundTransaction(expectedId);

        Assertions.assertEquals(expectedResponse, refoundResponse.getRefound());
    }

    private TransactionRequest createInvalidTransactionRequest() {
        final var expectedSourceAccount = "123456";
        final var expectedTargetAccount = "123456";
        final var expectedAmount = "100";

        TransactionRequest request = new TransactionRequest();
        request.setSourceAccount(expectedSourceAccount);
        request.setTargetAccount(expectedTargetAccount);
        request.setAmount(expectedAmount); 

        return request;
    }

    private TransactionRequest createValidTransactionRequest() {
        final var expectedSourceAccount = "123456";
        final var expectedTargetAccount = "654321";
        final var expectedAmount = "100";

        TransactionRequest request = new TransactionRequest();
        request.setSourceAccount(expectedSourceAccount);
        request.setTargetAccount(expectedTargetAccount);
        request.setAmount(expectedAmount); 

        return request;
    }

    private AccountDTO createAccountDTO() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedAccountCode = "123456";
        final var expectedUserId = "1";

        AccountDTO dto = new AccountDTO();
        dto.setId(expectedId);
        dto.setAccountCode(expectedAccountCode);
        dto.setUserId(expectedUserId);
        return dto;
    }

    private TransactionItem createTransactionItem() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedSourceAccount = "123456";
        final var expectedTargetAccount = "santander";
        final var expectedAmount = new BigDecimal("100");
        final var expectedTransactionType = TypeTransaction.INCOME;
        final var expectedTransaction = new Transaction();
        return new TransactionItem(expectedId, expectedSourceAccount, expectedTargetAccount, expectedAmount, expectedTransactionType, expectedTransaction);
    }

    private Transaction createTransaction() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedDevolution = false;
        final var expectedCreatedAt = LocalDateTime.now().toString();
        final var expectedTransactionItems = List.of(new TransactionItem());

        Transaction transaction = new Transaction();
        transaction.setId(expectedId);
        transaction.setDevolution(expectedDevolution);
        transaction.setCreatedAt(expectedCreatedAt);
        transaction.setTransactionItems(expectedTransactionItems);
        return transaction;
    }
}
