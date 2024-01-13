package dev.igor.apitransactions.error;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException() {
        this("Account not found", null);
    }

    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause, true, false);
    }
}
