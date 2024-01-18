package dev.igor.apitransactions.service.impl;

import dev.igor.apitransactions.api.request.TransactionRequest;
import dev.igor.apitransactions.api.response.RefoundResponse;
import dev.igor.apitransactions.api.response.TransactionResponse;
import dev.igor.apitransactions.client.AccountClient;
import dev.igor.apitransactions.dto.AccountDTO;
import dev.igor.apitransactions.dto.TransactionMessageDTO;
import dev.igor.apitransactions.error.ActionsYourSelfException;
import dev.igor.apitransactions.error.TransactionNotFoundException;
import dev.igor.apitransactions.event.SenderMQ;
import dev.igor.apitransactions.model.Transaction;
import dev.igor.apitransactions.model.TransactionItem;
import dev.igor.apitransactions.model.enums.TypeTransaction;
import dev.igor.apitransactions.repository.TransactionItemRepository;
import dev.igor.apitransactions.repository.TransactionRepository;
import dev.igor.apitransactions.service.TransactionService;
import dev.igor.apitransactions.service.command.TransactionCommandHandler;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository repository;
    private final TransactionItemRepository itemRepository;
    private final AccountClient accountClient;
    private final TransactionCommandHandler commandHandler;
    private final SenderMQ senderMQ;

    public TransactionServiceImpl(TransactionRepository repository, TransactionItemRepository itemRepository, AccountClient accountClient, TransactionCommandHandler commandHandler, SenderMQ senderMQ) {
        this.repository = repository;
        this.itemRepository = itemRepository;
        this.accountClient = accountClient;
        this.commandHandler = commandHandler;
        this.senderMQ = senderMQ;
    }

    @Override
    public TransactionResponse createTransaction(TransactionRequest request) throws JsonProcessingException {
        if (request.getTargetAccount().equals(request.getSourceAccount())) {
            throw new ActionsYourSelfException();
        }
        AccountDTO accountSource = accountClient.findByAccountCode(request.getSourceAccount());
        AccountDTO accountTarget = accountClient.findByAccountCode(request.getTargetAccount());

        request.setSourceAccount(accountSource.getId());
        request.setTargetAccount(accountTarget.getId());

        Transaction transaction = Transaction.create();

        TransactionItem outcome = commandHandler.handler(TypeTransaction.OUTCOME, accountSource, request, transaction);
        TransactionItem income = commandHandler.handler(TypeTransaction.INCOME, accountTarget, request, transaction);

        transaction.setTransactionItems(List.of(income, outcome));
        repository.save(transaction);
        return TransactionResponse.of(transaction);
    }

    @Override
    public RefoundResponse refundTransaction(String id) throws JsonProcessingException {
        Optional<Transaction> transactionExists = repository.findById(id);
        if (transactionExists.isEmpty()) {
            throw new TransactionNotFoundException();
        }
        Transaction transaction = transactionExists.get();
        TransactionItem transactionItem = itemRepository.findByTransaction(transaction.getId()).get(0);

        TransactionMessageDTO message = new TransactionMessageDTO();
        message.setSourceAccount(transactionItem.getSourceAccount());
        message.setTargetAccount(transactionItem.getTargetAccount());
        message.setAmount(transactionItem.getAmount().toString());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(message);

        senderMQ.sendDevolution(json);

        transaction.setDevolution(true);
        repository.save(transaction);
        return new RefoundResponse("true");
    }
}
