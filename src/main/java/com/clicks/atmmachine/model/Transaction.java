package com.clicks.atmmachine.model;

import com.clicks.atmmachine.enums.TransactionType;

import java.time.LocalDateTime;

/**
 * A model representing a Transaction
 * belonging to an account number
 */
public class Transaction {

    private String id;
    private TransactionType type;
    private Double amount;
    private Double fee;
    private Account account;
    private Boolean isCompleted;
    private LocalDateTime createdAt;

    public Transaction(String id, TransactionType type, Double amount, Double fee, Account account, Boolean isCompleted, LocalDateTime createdAt) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.fee = fee;
        this.account = account;
        this.isCompleted = isCompleted;
        this.createdAt = createdAt;
    }

    public Transaction() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {

        return String.format("%s\t\t\t%s\t\t%.2f\t\t\t%.2f\t%s\t\t\t%s\t\t\t\t\t%s",
                id.substring(0, 8), type.name(), amount, fee, account.getAccountNumber(), isCompleted, createdAt);

    }
}
