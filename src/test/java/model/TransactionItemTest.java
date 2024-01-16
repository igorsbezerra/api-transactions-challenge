package model;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.igor.apitransactions.api.request.TransactionRequest;
import dev.igor.apitransactions.model.Transaction;
import dev.igor.apitransactions.model.TransactionItem;
import dev.igor.apitransactions.model.enums.TypeTransaction;

@ExtendWith(MockitoExtension.class)
public class TransactionItemTest {
    @Test
    void getter_and_setter() {
        final var expectedId = UUID.randomUUID().toString();
        final var expectedSourceAccount = "123456";
        final var expectedTargetAccount = "santander";
        final var expectedAmount = new BigDecimal("100");
        final var expectedTransactionType = TypeTransaction.INCOME;
        final var expectedTransaction = new Transaction();

        TransactionItem item = new TransactionItem();
        item.setId(expectedId);
        item.setSourceAccount(expectedSourceAccount);
        item.setTargetAccount(expectedTargetAccount);
        item.setAmount(expectedAmount);
        item.setType(expectedTransactionType);
        item.setTransaction(expectedTransaction);

        Assertions.assertEquals(expectedId, item.getId());
        Assertions.assertEquals(expectedSourceAccount, item.getSourceAccount());
        Assertions.assertEquals(expectedTargetAccount, item.getTargetAccount());
        Assertions.assertEquals(expectedAmount, item.getAmount());
        Assertions.assertEquals(expectedTransactionType, item.getType());
        Assertions.assertEquals(expectedTransaction, item.getTransaction());

        TransactionItem item2 = new TransactionItem(expectedId, expectedSourceAccount, expectedTargetAccount, expectedAmount, expectedTransactionType, expectedTransaction);

        Assertions.assertEquals(expectedId, item2.getId());
        Assertions.assertEquals(expectedSourceAccount, item2.getSourceAccount());
        Assertions.assertEquals(expectedTargetAccount, item2.getTargetAccount());
        Assertions.assertEquals(expectedAmount, item2.getAmount());
        Assertions.assertEquals(expectedTransactionType, item2.getType());
        Assertions.assertEquals(expectedTransaction, item2.getTransaction());
    }

    @Test
    void create_transation_item_method_factory() {
        final var expectedSourceAccount = "123456";
        final var expectedTargetAccount = "654321";
        final var expectedAmount = "100";
        TransactionRequest request = new TransactionRequest();
        request.setSourceAccount(expectedSourceAccount);
        request.setTargetAccount(expectedTargetAccount);
        request.setAmount(expectedAmount);

        TransactionItem item = TransactionItem.create(request);

        Assertions.assertNotNull(item.getId());
        Assertions.assertEquals(request.getSourceAccount(), item.getSourceAccount());
        Assertions.assertEquals(request.getTargetAccount(), item.getTargetAccount());
    }
}
