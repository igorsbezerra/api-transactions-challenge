package dev.igor.apitransactions.error;

public class UnavailableAccountException extends RuntimeException {
    public UnavailableAccountException() {
        this("Canceled transaction", null);
    }

    public UnavailableAccountException(String message, Throwable cause) {
        super(message, cause, true, false);
    }
}
