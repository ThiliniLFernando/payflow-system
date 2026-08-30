package dev.thilinifernando.account_service.dto;

public record AccountValidationStatusResponse(
        String accountNumber,
        String validationStatus
) {
}
