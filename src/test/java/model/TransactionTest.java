package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.igor.apitransactions.model.Transaction;
import dev.igor.apitransactions.model.TransactionItem;

@ExtendWith(MockitoExtension.class)
public class TransactionTest {
    @Test
    void getter_and_setter() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedDevolution = false;
        final var expectedCreatedAt = LocalDateTime.now();;
        final var expectedTransactionItems = List.of(new TransactionItem());

        Transaction transaction = new Transaction();
        transaction.setId(expectedId);
        transaction.setDevolution(expectedDevolution);
        transaction.setCreatedAt(expectedCreatedAt);
        transaction.setTransactionItems(expectedTransactionItems);

        Assertions.assertEquals(expectedId, transaction.getId());
        Assertions.assertEquals(expectedDevolution, transaction.isDevolution());
        Assertions.assertEquals(expectedCreatedAt, transaction.getCreatedAt());
        Assertions.assertEquals(expectedTransactionItems, transaction.getTransactionItems());
    }

    @Test
    void create_tranasction_method_factory() {
        Transaction transaction = Transaction.create();
        
        Assertions.assertNotNull(transaction.getId());
        Assertions.assertNotNull(transaction.getCreatedAt());
        Assertions.assertFalse(transaction.isDevolution());
        Assertions.assertNull(transaction.getTransactionItems());
    }
}
