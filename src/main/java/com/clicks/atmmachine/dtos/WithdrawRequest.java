package com.clicks.atmmachine.dtos;

import com.clicks.atmmachine.model.Account;

/**
 * DTO class that represents a particular request for funds withdrawal
 * with a specified amount
 */
public record WithdrawRequest(Account account, double amount) {
}
