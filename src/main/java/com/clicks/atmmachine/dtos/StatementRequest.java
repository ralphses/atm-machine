package com.clicks.atmmachine.dtos;

import com.clicks.atmmachine.model.Account;

import java.time.LocalDateTime;

/**
 * DTO class that represents a particular request for account statement
 * with start date an end date for a particular account
 */
public record StatementRequest(Account account, LocalDateTime start, LocalDateTime end) {
}
