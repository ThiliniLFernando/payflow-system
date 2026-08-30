package dev.thilinifernando.account_service.dto;

import java.math.BigDecimal;

public record AccountBalanceResponse(
        String accountNumber,
        BigDecimal balance,
        String currency
) {
}
