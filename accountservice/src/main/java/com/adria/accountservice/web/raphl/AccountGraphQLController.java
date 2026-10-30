package com.adria.accountservice.web.raphql;

import com.adria.accountservice.domain.Account;
import com.adria.accountservice.service.AccountService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class AccountGraphQLController {

    private final AccountService service;

    public AccountGraphQLController(AccountService service) {
        this.service = service;
    }

    @QueryMapping
    public Account accountById(@Argument String id) {
        return service.findById(UUID.fromString(id));
    }

    @QueryMapping
    public List<Account> allAccounts() {
        return service.findAll();
    }
}