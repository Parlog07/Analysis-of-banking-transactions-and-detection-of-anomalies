package entity;

import java.time.LocalDateTime;

public record Transaction(
        int id,
        LocalDateTime date,
        double amount,
        TransactionType type,
        String location,
        int accountId
) {
}