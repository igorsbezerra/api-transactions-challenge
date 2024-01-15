package dev.igor.apitransactions.service;

import dev.igor.apitransactions.api.request.TransactionRequest;
import dev.igor.apitransactions.api.response.TransactionResponse;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest request);
}
