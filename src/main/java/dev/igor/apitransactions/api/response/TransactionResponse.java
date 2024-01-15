package dev.igor.apitransactions.api.response;

import dev.igor.apitransactions.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransactionResponse {
    private String id;

    public static TransactionResponse of(Transaction transaction) {
        return new TransactionResponse(transaction.getId());
    }
}
