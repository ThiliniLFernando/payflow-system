package dev.thilinifernando.account_service.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record InternalTransactionCreatedEvent(
        String transactionId,
        String userId,
        String accountNumber,
        BigDecimal amount,
        String currency,
        String status,
        String source,
        LocalDateTime createdAt
) {
}
