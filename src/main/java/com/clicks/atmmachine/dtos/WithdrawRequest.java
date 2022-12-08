package com.clicks.atmmachine.dtos;

import com.clicks.atmmachine.model.Account;

public record WithdrawRequest(Account account, double amount) {
}
