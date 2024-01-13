package dev.igor.apitransactions.service.command;

import dev.igor.apitransactions.api.request.TransactionRequest;
import dev.igor.apitransactions.api.response.TransactionResponse;
import dev.igor.apitransactions.dto.AccountDTO;

public interface CommandHandler {
    TransactionResponse command(AccountDTO account, TransactionRequest request);
}
