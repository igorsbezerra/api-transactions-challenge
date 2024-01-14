package dev.igor.apitransactions.api.controller;

import dev.igor.apitransactions.api.request.TransactionRequest;
import dev.igor.apitransactions.service.TransactionService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Void> createTransaction(@RequestBody() TransactionRequest request) {
        transactionService.createTransaction(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
