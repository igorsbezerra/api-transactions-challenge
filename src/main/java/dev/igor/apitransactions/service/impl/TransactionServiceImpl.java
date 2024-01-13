package dev.igor.apitransactions.service.impl;

import dev.igor.apitransactions.api.request.TransactionRequest;
import dev.igor.apitransactions.client.AccountClient;
import dev.igor.apitransactions.dto.AccountDTO;
import dev.igor.apitransactions.error.ActionsYourSelfException;
import dev.igor.apitransactions.model.enums.TypeTransaction;
import dev.igor.apitransactions.service.TransactionService;
import dev.igor.apitransactions.service.command.TransactionCommandHandler;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final AccountClient accountClient;
    private final TransactionCommandHandler commandHandler;

    public TransactionServiceImpl(AccountClient accountClient, TransactionCommandHandler commandHandler) {
        this.accountClient = accountClient;
        this.commandHandler = commandHandler;
    }

    @Override
    public void createTransaction(TransactionRequest request) {
        if (request.getTargetAccount().equals(request.getSourceAccount())) {
            throw new ActionsYourSelfException();
        }
        AccountDTO accountSource = accountClient.findByAccountCode(request.getSourceAccount());
        AccountDTO accountTarget = accountClient.findByAccountCode(request.getTargetAccount());

        request.setSourceAccount(accountSource.getId());
        request.setTargetAccount(accountTarget.getId());

        commandHandler.handler(TypeTransaction.INCOME, accountTarget, request);
        commandHandler.handler(TypeTransaction.OUTCOME, accountSource, request);
    }
}
