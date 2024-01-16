package dev.igor.apitransactions.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @Column(name = "id", length = 36, nullable = false)
    private String id;
    private boolean devolution;
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    private List<TransactionItem> transactionItems;

    public Transaction(String id, boolean devolution, LocalDateTime createdAt) {
        this.id = id;
        this.devolution = devolution;
        this.createdAt = createdAt;
    }

    public static Transaction create() {
        String id = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        boolean devolution = false;
        return new Transaction(id, devolution, now);
    }
}
