package dev.thilinifernando.account_service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AccountDetails(
        String userId,
        String accountNumber,
        String accountHolderName,
        String accountType,
        String currency,
        BigDecimal balance,
        String status,
        String bankCode,
        String branchCode,
        String iban,
        String nicNumber,
        String passportNumber,
        String nationality,
        LocalDate birthDate,
        String address,
        String phoneNumber,
        String email
) {
}
