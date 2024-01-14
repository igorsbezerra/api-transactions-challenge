package dev.igor.apitransactions.service.command;

import dev.igor.apitransactions.api.request.TransactionRequest;
import dev.igor.apitransactions.dto.AccountDTO;
import dev.igor.apitransactions.model.Transaction;
import dev.igor.apitransactions.model.TransactionItem;
import dev.igor.apitransactions.model.enums.TypeTransaction;

public interface TransactionCommandHandler {
    TransactionItem handler(TypeTransaction type, AccountDTO account, TransactionRequest request, Transaction transaction);
}
