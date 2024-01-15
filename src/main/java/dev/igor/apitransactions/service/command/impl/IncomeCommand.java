package dev.igor.apitransactions.service.command.impl;

import dev.igor.apitransactions.api.request.TransactionRequest;
import dev.igor.apitransactions.client.NotificationClient;
import dev.igor.apitransactions.dto.AccountDTO;
import dev.igor.apitransactions.event.IncomeSenderMQ;
import dev.igor.apitransactions.model.Transaction;
import dev.igor.apitransactions.model.TransactionItem;
import dev.igor.apitransactions.model.enums.TypeTransaction;
import dev.igor.apitransactions.service.command.CommandHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class IncomeCommand implements CommandHandler {

    private final ObjectMapper mapper;
    private final IncomeSenderMQ senderMQ;
    private final NotificationClient notification;

    public IncomeCommand(ObjectMapper mapper, IncomeSenderMQ senderMQ, NotificationClient notification) {
        this.mapper = mapper;
        this.senderMQ = senderMQ;
        this.notification = notification;
    }

    @Override
    public TransactionItem command(AccountDTO account, TransactionRequest request, Transaction transaction) {
        TransactionItem transactionItem = TransactionItem.create(request);
        transactionItem.setType(TypeTransaction.INCOME);
        transactionItem.setTransaction(transaction);

        String json = "";
        try {
            json = mapper.writeValueAsString(transactionItem);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to generate json string");
        }

        senderMQ.sendIncome(json);
        notification.sent();

        return transactionItem;
    }
}
