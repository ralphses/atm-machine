package com.clicks.atmmachine.dtos;

import com.clicks.atmmachine.model.Account;

public record TransferRequest(Account account, String toAccountNumber, double amount) {
}
