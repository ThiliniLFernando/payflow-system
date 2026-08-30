package dev.thilinifernando.payment_service.util.response;

import java.math.BigDecimal;

public record AccountResponse(
    String userId,
    String accountNumber,
    BigDecimal balance,
    String currency
) {
}
