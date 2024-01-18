package dev.igor.apitransactions.repository;

import dev.igor.apitransactions.model.TransactionItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionItemRepository extends JpaRepository<TransactionItem, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM transaction_item WHERE transaction_id = ?")
    List<TransactionItem> findByTransaction(String id);
}
