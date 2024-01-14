package dev.igor.apitransactions.model;

import dev.igor.apitransactions.api.request.TransactionRequest;
import dev.igor.apitransactions.model.enums.TypeTransaction;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "transaction_item")
public class TransactionItem {
    @Id
    @Column(name = "id", length = 36, nullable = false)
    private String id;
    @Column(name = "source_account_id", length = 36)
    private String sourceAccount;
    @Column(name = "target_account_id", length = 36)
    private String targetAccount;
    @Column(precision = 10, scale = 2)
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private TypeTransaction type;
    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    public TransactionItem(String id, String sourceAccount, String targetAccount, BigDecimal amount) {
        this.id = id;
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
        this.amount = amount;
    }

    public static TransactionItem create(TransactionRequest request) {
        String id = UUID.randomUUID().toString();
        BigDecimal value2 = new BigDecimal(request.getAmount());
        return new TransactionItem(id, request.getSourceAccount(), request.getTargetAccount(), value2);
    }
}
