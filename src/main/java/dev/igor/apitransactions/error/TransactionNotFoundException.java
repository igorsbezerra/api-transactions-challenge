package dev.igor.apitransactions.error;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException() {
        this("Transaction not found", null);
    }

    public TransactionNotFoundException(String message, Throwable cause) {
        super(message, cause, true, false);
    }
}
