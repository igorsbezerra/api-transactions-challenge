package dev.igor.apitransactions.service.impl;

import dev.igor.apitransactions.api.request.TransactionRequest;
import dev.igor.apitransactions.client.AccountClient;
import dev.igor.apitransactions.dto.AccountDTO;
import dev.igor.apitransactions.error.ActionsYourSelfException;
import dev.igor.apitransactions.model.Transaction;
import dev.igor.apitransactions.model.TransactionItem;
import dev.igor.apitransactions.model.enums.TypeTransaction;
import dev.igor.apitransactions.repository.TransactionItemRepository;
import dev.igor.apitransactions.repository.TransactionRepository;
import dev.igor.apitransactions.service.TransactionService;
import dev.igor.apitransactions.service.command.TransactionCommandHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository repository;
    private final TransactionItemRepository itemRepository;
    private final AccountClient accountClient;
    private final TransactionCommandHandler commandHandler;

    public TransactionServiceImpl(TransactionRepository repository, TransactionItemRepository itemRepository, AccountClient accountClient, TransactionCommandHandler commandHandler) {
        this.repository = repository;
        this.itemRepository = itemRepository;
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

        Transaction transaction = Transaction.create();

        TransactionItem income = commandHandler.handler(TypeTransaction.INCOME, accountTarget, request, transaction);
        TransactionItem outcome = commandHandler.handler(TypeTransaction.OUTCOME, accountSource, request, transaction);

        transaction.setTransactionItems(List.of(income, outcome));
        repository.save(transaction);
    }
}
