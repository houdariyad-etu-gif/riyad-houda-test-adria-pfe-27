package com.adria.transactionservice.web.rest;

import com.adria.transactionservice.domain.Transaction;
import com.adria.transactionservice.service.TransactionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionRestController {
    private final TransactionService service;

    public TransactionRestController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public Transaction create(@RequestBody Transaction tx) {
        return service.create(tx);
    }
}