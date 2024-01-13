package dev.igor.apitransactions.repository;

import dev.igor.apitransactions.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
