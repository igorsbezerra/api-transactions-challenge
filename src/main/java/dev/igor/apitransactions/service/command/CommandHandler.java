package dev.igor.apitransactions.service.command;

import dev.igor.apitransactions.api.request.TransactionRequest;
import dev.igor.apitransactions.dto.AccountDTO;
import dev.igor.apitransactions.model.Transaction;
import dev.igor.apitransactions.model.TransactionItem;

public interface CommandHandler {
    TransactionItem command(AccountDTO account, TransactionRequest request, Transaction transaction);
}
