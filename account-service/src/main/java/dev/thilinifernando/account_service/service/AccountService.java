package dev.thilinifernando.account_service.service;

import dev.thilinifernando.account_service.dto.AccountBalanceResponse;
import dev.thilinifernando.account_service.dto.AccountDetails;
import dev.thilinifernando.account_service.dto.AccountValidationStatusResponse;
import dev.thilinifernando.account_service.entity.Account;
import dev.thilinifernando.account_service.repo.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDetails getAccountDetails(String userId) {
        Optional<Account> account = accountRepository.findByUserId(userId);
        return account.map(value -> new AccountDetails(
                        value.getUserId(),
                        value.getAccountNumber(),
                        value.getAccountHolderName(),
                        value.getAccountType().name(),
                        value.getCurrency(),
                        value.getBalance(),
                        value.getStatus().name(),
                        value.getBankCode(),
                        value.getBranchCode(),
                        value.getIban(),
                        value.getNicNumber(),
                        value.getPassportNumber(),
                        value.getNationality(),
                        value.getBirthDate(),
                        value.getAddress(),
                        value.getPhoneNumber(),
                        value.getEmail()))
                .orElseThrow(RuntimeException::new);
    }

    public AccountValidationStatusResponse getValidationStatusByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(RuntimeException::new);

        return new AccountValidationStatusResponse(
                account.getAccountNumber(),
                account.getStatus().name()
        );
    }

    public AccountBalanceResponse getBalanceByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(RuntimeException::new);

        return new AccountBalanceResponse(
                account.getAccountNumber(),
                account.getBalance(),
                account.getCurrency()
        );
    }
}
