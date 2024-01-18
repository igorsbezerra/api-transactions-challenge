package dev.igor.apitransactions.api.controller;

import dev.igor.apitransactions.api.request.TransactionRequest;
import dev.igor.apitransactions.api.response.RefoundResponse;
import dev.igor.apitransactions.api.response.TransactionResponse;
import dev.igor.apitransactions.service.TransactionService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody() TransactionRequest request) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.createTransaction(request));
    }

    @PostMapping("/{id}/devolution")
    public ResponseEntity<RefoundResponse> refundTransaction(@PathVariable("id") String id) {
        return ResponseEntity.ok(transactionService.refundTransaction(id));
    }
}
