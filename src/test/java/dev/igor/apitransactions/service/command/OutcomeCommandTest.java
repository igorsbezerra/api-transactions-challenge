package dev.igor.apitransactions.service.command;

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

import dev.igor.apitransactions.api.request.TransactionRequest;
import dev.igor.apitransactions.client.AccountClient;
import dev.igor.apitransactions.client.NotificationClient;
import dev.igor.apitransactions.dto.AccountDTO;
import dev.igor.apitransactions.dto.AvailableAccount;
import dev.igor.apitransactions.error.UnavailableAccountException;
import dev.igor.apitransactions.event.OutcomeSenderMQ;
import dev.igor.apitransactions.model.Transaction;
import dev.igor.apitransactions.model.TransactionItem;
import dev.igor.apitransactions.service.command.impl.OutcomeCommand;

@ExtendWith(MockitoExtension.class)
public class OutcomeCommandTest {
    @Mock
    private AccountClient accountClient;
    @Mock
    private OutcomeSenderMQ senderMQ;
    @Mock
    private NotificationClient notificationClient;
    @InjectMocks
    private OutcomeCommand outcomeCommand;

    @Test
    void must_execute_pattern_command_outcome() {
        Transaction transaction = createTransaction();
        TransactionRequest request = createTransactionRequest();
        AccountDTO accountDTO = createAccountDTO();
        AvailableAccount availableAccount = createAvailableAccount();
        Mockito.doNothing().when(senderMQ).sendOutcome(Mockito.anyString());
        Mockito.doNothing().when(notificationClient).sent();
        Mockito.when(accountClient.availableBalance(Mockito.anyString(), Mockito.anyString())).thenReturn(availableAccount);

        TransactionItem command = outcomeCommand.command(accountDTO, request, transaction);

        Assertions.assertNotNull(command.getId());
    }

    @Test
    void should_not_execute_pattern_command_income_because_account_is_not_available() {
        Transaction transaction = createTransaction();
        TransactionRequest request = createTransactionRequest();
        AccountDTO accountDTO = createAccountDTO();
        AvailableAccount unavailableAccount = createUnavailableAccount();
        Mockito.when(accountClient.availableBalance(Mockito.anyString(), Mockito.anyString())).thenReturn(unavailableAccount);

        Assertions.assertThrows(UnavailableAccountException.class, () -> outcomeCommand.command(accountDTO, request, transaction));
    }

    private Transaction createTransaction() {
        Transaction transaction = new Transaction();
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

    private Transaction createTransactionInvalidJson() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedDevolution = false;
        final var expectedCreatedAt = LocalDateTime.now();;
        final var expectedTransactionItems = List.of(new TransactionItem());

        Transaction transaction = new Transaction();
        transaction.setId(expectedId);
        transaction.setDevolution(expectedDevolution);
        transaction.setCreatedAt(expectedCreatedAt);
        transaction.setTransactionItems(expectedTransactionItems);
        return transaction;
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

    private AvailableAccount createAvailableAccount() {
        AvailableAccount availableAccount = new AvailableAccount();
        availableAccount.setAvailable("true");
        return availableAccount;
    }

    private AvailableAccount createUnavailableAccount() {
        AvailableAccount availableAccount = new AvailableAccount();
        availableAccount.setAvailable("false");
        return availableAccount;
    }
}
