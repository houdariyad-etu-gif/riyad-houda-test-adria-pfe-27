package com.adria.accountservice.service;

import com.adria.accountservice.domain.Account;
import com.adria.accountservice.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found: " + id));
    }

    public Account save(Account account) {
        if (account.getId() == null) {
            account.setId(UUID.randomUUID());
        }
        return repository.save(account);
    }

    public List<Account> findAll() {
        return repository.findAll();
    }

    public Account updateBalance(UUID id, double newBalance) {
        Account account = findById(id);
        account.setBalance(newBalance);
        return repository.save(account);
    }
}