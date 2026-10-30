package com.adria.accountservice.domain;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class Account {

    @Id
    private UUID id;

    private String customerReference;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    private String currency;

    private double balance;

    public Account() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getCustomerReference() { return customerReference; }
    public void setCustomerReference(String customerReference) { this.customerReference = customerReference; }

    public AccountStatus getStatus() { return status; }
    public void setStatus(AccountStatus status) { this.status = status; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}