package dev.igor.apitransactions.service.command;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonProcessingException;

import dev.igor.apitransactions.api.request.TransactionRequest;
import dev.igor.apitransactions.dto.AccountDTO;
import dev.igor.apitransactions.model.Transaction;
import dev.igor.apitransactions.model.TransactionItem;
import dev.igor.apitransactions.model.enums.TypeTransaction;
import dev.igor.apitransactions.service.command.impl.CommandApp;
import dev.igor.apitransactions.service.command.impl.IncomeCommand;
import dev.igor.apitransactions.service.command.impl.OutcomeCommand;

@ExtendWith(MockitoExtension.class)
public class CommandTest {
    @Mock
    private IncomeCommand incomeCommand;
    @Mock
    private OutcomeCommand outcomeCommand;
    @InjectMocks
    private CommandApp command;

    @Test
    void must_invoke_income_command() throws JsonProcessingException {
        Transaction transaction = createTransaction();
        TransactionItem item = createTransactionItem();
        TransactionRequest request = createTransactionRequest();
        AccountDTO accountDTO = createAccountDTO();
        Mockito.when(incomeCommand.command(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(item);

        TransactionItem handler = command.handler(TypeTransaction.INCOME, accountDTO, request, transaction);

        Assertions.assertEquals(item.getId(), handler.getId());
    }

    @Test
    void must_invoke_outcome_command() throws JsonProcessingException {
        Transaction transaction = createTransaction();
        TransactionItem item = createTransactionItem();
        TransactionRequest request = createTransactionRequest();
        AccountDTO accountDTO = createAccountDTO();
        Mockito.when(outcomeCommand.command(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(item);

        TransactionItem handler = command.handler(TypeTransaction.OUTCOME, accountDTO, request, transaction);

        Assertions.assertEquals(item.getId(), handler.getId());
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

    private TransactionRequest createTransactionRequest() {
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
}
