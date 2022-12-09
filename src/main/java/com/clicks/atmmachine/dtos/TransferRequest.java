package com.clicks.atmmachine.dtos;

import com.clicks.atmmachine.model.Account;

/**
 * DTO class that represents a particular request for Funds
 * from an account to another account
 */
public record TransferRequest(Account account, String toAccountNumber, double amount) {
}
