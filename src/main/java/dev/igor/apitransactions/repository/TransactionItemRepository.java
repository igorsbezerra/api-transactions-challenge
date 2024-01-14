package dev.igor.apitransactions.repository;

import dev.igor.apitransactions.model.TransactionItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionItemRepository extends JpaRepository<TransactionItem, String> {
}
