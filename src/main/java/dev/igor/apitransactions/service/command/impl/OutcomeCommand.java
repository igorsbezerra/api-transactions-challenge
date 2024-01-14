package dev.igor.apitransactions.service.command.impl;

import dev.igor.apitransactions.api.request.TransactionRequest;
import dev.igor.apitransactions.client.AccountClient;
import dev.igor.apitransactions.dto.AccountDTO;
import dev.igor.apitransactions.dto.AvailableAccount;
import dev.igor.apitransactions.error.UnavailableAccountException;
import dev.igor.apitransactions.event.OutcomeSenderMQ;
import dev.igor.apitransactions.model.Transaction;
import dev.igor.apitransactions.model.TransactionItem;
import dev.igor.apitransactions.model.enums.TypeTransaction;
import dev.igor.apitransactions.service.command.CommandHandler;
import org.springframework.stereotype.Component;

@Component
public class OutcomeCommand implements CommandHandler {
    private final AccountClient accountClient;
    private final OutcomeSenderMQ senderMQ;

    public OutcomeCommand(AccountClient accountClient, OutcomeSenderMQ senderMQ) {
        this.accountClient = accountClient;
        this.senderMQ = senderMQ;
    }

    @Override
    public TransactionItem command(AccountDTO account, TransactionRequest request, Transaction transaction) {
        TransactionItem transactionItem = TransactionItem.create(request);
        transactionItem.setType(TypeTransaction.OUTCOME);
        transactionItem.setTransaction(transaction);

        AvailableAccount availableAccount = accountClient.availableBalance(account.getAccountCode(), request.getAmount());
        if (!Boolean.parseBoolean(availableAccount.getAvailable())){
            throw new UnavailableAccountException();
        }
    
        senderMQ.sendOutcome("message :)");

        return transactionItem;
    }
}
