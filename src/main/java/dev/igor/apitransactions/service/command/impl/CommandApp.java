package dev.igor.apitransactions.service.command.impl;

import dev.igor.apitransactions.api.request.TransactionRequest;
import dev.igor.apitransactions.dto.AccountDTO;
import dev.igor.apitransactions.model.Transaction;
import dev.igor.apitransactions.model.TransactionItem;
import dev.igor.apitransactions.model.enums.TypeTransaction;
import dev.igor.apitransactions.service.command.CommandHandler;
import dev.igor.apitransactions.service.command.TransactionCommandHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CommandApp implements TransactionCommandHandler {
    private final IncomeCommand incomeCommand;
    private final OutcomeCommand outcomeCommand;

    public CommandApp(IncomeCommand incomeCommand, OutcomeCommand outcomeCommand) {
        this.incomeCommand = incomeCommand;
        this.outcomeCommand = outcomeCommand;
    }

    @Override
    public TransactionItem handler(TypeTransaction type, AccountDTO account, TransactionRequest request, Transaction transaction) {
        List<Map<TypeTransaction, CommandHandler>> commands =
                List.of(
                    Map.of(TypeTransaction.INCOME, incomeCommand),
                    Map.of(TypeTransaction.OUTCOME, outcomeCommand)
        );

        for (Map<TypeTransaction, CommandHandler> command : commands) {
            if (command.containsKey(type)) {
                CommandHandler commandHandler = command.get(type);
                return commandHandler.command(account, request, transaction);
            }
        }
        return null;
    }
}
