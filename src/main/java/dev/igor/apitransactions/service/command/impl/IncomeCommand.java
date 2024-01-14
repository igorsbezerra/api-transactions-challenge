package dev.igor.apitransactions.service.command.impl;

import dev.igor.apitransactions.api.request.TransactionRequest;
import dev.igor.apitransactions.dto.AccountDTO;
import dev.igor.apitransactions.model.Transaction;
import dev.igor.apitransactions.model.TransactionItem;
import dev.igor.apitransactions.model.enums.TypeTransaction;
import dev.igor.apitransactions.service.command.CommandHandler;
import org.springframework.stereotype.Component;

@Component
public class IncomeCommand implements CommandHandler {

    @Override
    public TransactionItem command(AccountDTO account, TransactionRequest request, Transaction transaction) {
        TransactionItem transactionItem = TransactionItem.create(request);
        transactionItem.setType(TypeTransaction.INCOME);
        transactionItem.setTransaction(transaction);

        //TOOD: Enviar para mensageria evento de incrementar saldo

        return transactionItem;
    }
}
