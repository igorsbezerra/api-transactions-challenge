package dev.igor.apitransactions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionMessageDTO {
    private String sourceAccount;
    private String targetAccount;
    private String amount;
}
