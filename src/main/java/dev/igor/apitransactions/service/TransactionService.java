package dev.igor.apitransactions.service;

import dev.igor.apitransactions.api.request.TransactionRequest;

public interface TransactionService {
    void createTransaction(TransactionRequest request);
}
