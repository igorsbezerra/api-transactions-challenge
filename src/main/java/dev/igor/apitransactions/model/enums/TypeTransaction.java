package dev.igor.apitransactions.model.enums;

public enum TypeTransaction {
    INCOME("income"),
    OUTCOME("outcome");

    private String value;

    TypeTransaction(String value) {
        this.value = value;
    }
}
