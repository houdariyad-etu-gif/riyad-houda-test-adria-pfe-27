package com.adria.accountservice.web.rest;

import com.adria.accountservice.domain.Account;
import com.adria.accountservice.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountRestController {

    private final AccountService service;

    public AccountRestController(AccountService service) {
        this.service = service;
    }

    @GetMapping
    public List<Account> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Account getById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    public Account create(@RequestBody Account account) {
        return service.save(account);
    }

    @PutMapping("/{id}/balance")
    public Account updateBalance(@PathVariable UUID id, @RequestParam double balance) {
        return service.updateBalance(id, balance);
    }
}