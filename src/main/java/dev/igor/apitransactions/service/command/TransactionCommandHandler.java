package dev.igor.apitransactions.service.command;

import dev.igor.apitransactions.api.request.TransactionRequest;
import dev.igor.apitransactions.api.response.TransactionResponse;
import dev.igor.apitransactions.dto.AccountDTO;
import dev.igor.apitransactions.model.enums.TypeTransaction;

public interface TransactionCommandHandler {
    TransactionResponse handler(TypeTransaction type, AccountDTO account, TransactionRequest request);
}
