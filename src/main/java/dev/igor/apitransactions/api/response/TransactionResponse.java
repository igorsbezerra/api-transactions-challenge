package dev.igor.apitransactions.api.response;

import dev.igor.apitransactions.model.TransactionItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransactionResponse {
    private String id;
    private String sourceAccount;
    private String targetAccount;
    private String type;

    public static TransactionResponse of(TransactionItem transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getSourceAccount(),
                transaction.getTargetAccount(),
                transaction.getType().toString());
    }
}
