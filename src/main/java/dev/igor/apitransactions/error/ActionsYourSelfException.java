package dev.igor.apitransactions.error;

public class ActionsYourSelfException extends RuntimeException {
    public ActionsYourSelfException() {
        this("It is not allowed to perform actions for yourself", null);
    }

    public ActionsYourSelfException(String message, Throwable cause) {
        super(message, cause, true, false);
    }
}
