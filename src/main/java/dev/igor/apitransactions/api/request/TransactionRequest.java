package dev.igor.apitransactions.api.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequest {
    @Size(min = 5, max = 5)
    @NotEmpty
    private String sourceAccount;
    @Size(min = 5, max = 5)
    @NotEmpty
    private String targetAccount;
    @NotEmpty
    private String amount;
}
