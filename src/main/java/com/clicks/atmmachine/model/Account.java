package com.clicks.atmmachine.model;

import com.clicks.atmmachine.enums.AccountType;

import java.util.Set;

/**
 * Model representing an account
 * belonging to a particular customer
 *
 * It also has ATM cards
 */
public class Account {

    private String accountNumber;
    private AccountType accountType;
    private Double currentBalance;
    private Set<AtmCard> atmCards;

    public Account(String accountNumber, AccountType accountType, Double currentBalance, Set<AtmCard> atmCards) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.currentBalance = currentBalance;
        this.atmCards = atmCards;
    }

    public Account() {}

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Set<AtmCard> getAtmCards() {
        return atmCards;
    }

    public void setAtmCards(Set<AtmCard> atmCards) {
        this.atmCards = atmCards;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", accountType=" + accountType +
                ", currentBalance=" + currentBalance +
                ", atmCards=" + atmCards +
                '}';
    }
}
