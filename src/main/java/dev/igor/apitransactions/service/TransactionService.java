package dev.igor.apitransactions.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import dev.igor.apitransactions.api.request.TransactionRequest;
import dev.igor.apitransactions.api.response.TransactionResponse;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest request) throws JsonProcessingException;
}
