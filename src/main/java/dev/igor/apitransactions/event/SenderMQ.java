package dev.igor.apitransactions.event;

public interface SenderMQ {
    void sendIncome(String message);
    void sendOutCome(String message);
    void sendDevolution(String message);
}
