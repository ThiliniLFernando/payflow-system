package dev.thilinifernando.account_service.controller;

import dev.thilinifernando.account_service.dto.AccountBalanceResponse;
import dev.thilinifernando.account_service.dto.AccountDetails;
import dev.thilinifernando.account_service.dto.AccountValidationStatusResponse;
import dev.thilinifernando.account_service.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{userId}")
    public AccountDetails getAccountDetails(@PathVariable String userId) {
        return accountService.getAccountDetails(userId);
    }

    @GetMapping("/account-number/{accountNumber}/validation-status")
    public AccountValidationStatusResponse getValidationStatusByAccountNumber(@PathVariable String accountNumber) {
        return accountService.getValidationStatusByAccountNumber(accountNumber);
    }

    @GetMapping("/account-number/{accountNumber}/balance")
    public AccountBalanceResponse getBalanceByAccountNumber(@PathVariable String accountNumber) {
        return accountService.getBalanceByAccountNumber(accountNumber);
    }
}
