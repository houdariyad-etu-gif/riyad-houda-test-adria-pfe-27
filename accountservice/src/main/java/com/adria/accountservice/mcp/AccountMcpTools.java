package com.adria.accountservice.mcp;

import com.adria.accountservice.domain.Account;
import com.adria.accountservice.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Outils exposés au chatbot agentique (MCP).
 * Chaque méthode publique = un "tool" utilisable par l'agent AI.
 */
@Service
public class AccountMcpTools {

    private final AccountService service;

    public AccountMcpTools(AccountService service) {
        this.service = service;
    }

    public Account getAccountById(String accountId) {
        return service.findById(UUID.fromString(accountId));
    }

    public double getBalance(String accountId) {
        return service.findById(UUID.fromString(accountId)).getBalance();
    }
}