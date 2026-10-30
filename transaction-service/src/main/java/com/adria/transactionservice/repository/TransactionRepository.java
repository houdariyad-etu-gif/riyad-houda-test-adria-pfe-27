package com.adria.transactionservice.repository;

import com.adria.transactionservice.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}