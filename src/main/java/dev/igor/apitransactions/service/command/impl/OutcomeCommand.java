package dev.igor.apitransactions.service.command.impl;

import dev.igor.apitransactions.api.request.TransactionRequest;
import dev.igor.apitransactions.api.response.TransactionResponse;
import dev.igor.apitransactions.dto.AccountDTO;
import dev.igor.apitransactions.model.Transaction;
import dev.igor.apitransactions.model.enums.TypeTransaction;
import dev.igor.apitransactions.repository.TransactionRepository;
import dev.igor.apitransactions.service.command.CommandHandler;
import org.springframework.stereotype.Component;

@Component
public class OutcomeCommand implements CommandHandler {
    private final TransactionRepository repository;

    public OutcomeCommand(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public TransactionResponse command(AccountDTO account, TransactionRequest request) {
        Transaction transaction = Transaction.create(request);
        transaction.setType(TypeTransaction.OUTCOME);

        //TOOD: Validar saldo
        //TOOD: Enviar para mensageria evento de retirar saldo

        return TransactionResponse.of(repository.save(transaction));
    }
}
