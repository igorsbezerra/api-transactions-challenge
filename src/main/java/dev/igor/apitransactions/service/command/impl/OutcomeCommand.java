package dev.igor.apitransactions.service.command.impl;

import dev.igor.apitransactions.api.request.TransactionRequest;
import dev.igor.apitransactions.api.response.TransactionResponse;
import dev.igor.apitransactions.dto.AccountDTO;
import dev.igor.apitransactions.model.Transaction;
import dev.igor.apitransactions.model.TransactionItem;
import dev.igor.apitransactions.model.enums.TypeTransaction;
import dev.igor.apitransactions.service.command.CommandHandler;
import org.springframework.stereotype.Component;

@Component
public class OutcomeCommand implements CommandHandler {
    @Override
    public TransactionItem command(AccountDTO account, TransactionRequest request, Transaction transaction) {
        TransactionItem transactionItem = TransactionItem.create(request);
        transactionItem.setType(TypeTransaction.OUTCOME);
        transactionItem.setTransaction(transaction);

        //TOOD: Validar saldo
        //TOOD: Enviar para mensageria evento de retirar saldo

        return transactionItem;
    }
}
