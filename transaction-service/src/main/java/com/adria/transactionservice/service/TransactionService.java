package com.adria.transactionservice.service;

import com.adria.transactionservice.client.AccountClient;
import com.adria.transactionservice.domain.Transaction;
import com.adria.transactionservice.repository.TransactionRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Service
public class TransactionService {
    private final TransactionRepository repo;
    private final AccountClient accountClient;
    private final KafkaTemplate<String, String> kafka;

    @Value("${bank.transaction.max-amount:1000}")
    private double maxAmount;

    public TransactionService(TransactionRepository repo, AccountClient accountClient, KafkaTemplate<String, String> kafka) {
        this.repo = repo;
        this.accountClient = accountClient;
        this.kafka = kafka;
    }

    @CircuitBreaker(name = "accountService", fallbackMethod = "fallback")
    public Transaction create(Transaction tx) {
        if (tx.getAmount().doubleValue() <= 0 || tx.getAmount().doubleValue() > maxAmount)
            throw new RuntimeException("Montant invalide (doit être > 0 et <= " + maxAmount + ")");

        Map<String, Object> acc = accountClient.getAccount(tx.getAccountId());

        if (!"ACTIVE".equals(acc.get("status")))
            throw new RuntimeException("Compte inactif");

        if (!tx.getCurrency().equals(acc.get("currency")))
            throw new RuntimeException("Devise incompatible");

        tx.setId(UUID.randomUUID());
        tx.setCreatedAt(Instant.now());
        Transaction saved = repo.save(tx);

        double newBalance = ((Number) acc.get("balance")).doubleValue() - tx.getAmount().doubleValue();
        accountClient.updateBalance(tx.getAccountId(), newBalance);

        String event = String.format("{\"eventId\":\"%s\",\"eventType\":\"TransactionCreated\",\"transactionId\":\"%s\",\"accountId\":\"%s\",\"amount\":%s,\"currency\":\"%s\",\"occurredAt\":\"%s\"}",
                UUID.randomUUID(), saved.getId(), saved.getAccountId(), saved.getAmount(), saved.getCurrency(), saved.getCreatedAt());

        kafka.send("bank.transactions.created", saved.getAccountId().toString(), event);
        return saved;
    }

    public Transaction fallback(Transaction tx, Exception e) {
        throw new RuntimeException("Service compte indisponible: " + e.getMessage());
    }
}