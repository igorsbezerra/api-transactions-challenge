package dev.igor.apitransactions.service.command.impl;

import dev.igor.apitransactions.api.request.TransactionRequest;
import dev.igor.apitransactions.client.AccountClient;
import dev.igor.apitransactions.client.NotificationClient;
import dev.igor.apitransactions.dto.AccountDTO;
import dev.igor.apitransactions.dto.AvailableAccount;
import dev.igor.apitransactions.error.UnavailableAccountException;
import dev.igor.apitransactions.event.SenderMQ;
import dev.igor.apitransactions.model.Transaction;
import dev.igor.apitransactions.model.TransactionItem;
import dev.igor.apitransactions.model.enums.TypeTransaction;
import dev.igor.apitransactions.service.command.CommandHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class OutcomeCommand implements CommandHandler {
    private final AccountClient accountClient;
    private final SenderMQ senderMQ;
    private final NotificationClient notification;

    public OutcomeCommand(AccountClient accountClient, SenderMQ senderMQ, NotificationClient notification) {
        this.accountClient = accountClient;
        this.senderMQ = senderMQ;
        this.notification = notification;
    }

    @Override
    public TransactionItem command(AccountDTO account, TransactionRequest request, Transaction transaction) throws JsonProcessingException {
        TransactionItem transactionItem = TransactionItem.create(request);
        transactionItem.setType(TypeTransaction.OUTCOME);
        transactionItem.setTransaction(transaction);

        AvailableAccount availableAccount = accountClient.availableBalance(account.getAccountCode(), request.getAmount());
        if (!Boolean.parseBoolean(availableAccount.getAvailable())){
            throw new UnavailableAccountException();
        }

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(transactionItem);

        senderMQ.sendOutCome(json);
        notification.sent();

        return transactionItem;
    }
}
