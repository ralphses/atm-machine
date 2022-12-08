package com.clicks.atmmachine.dtos;

import com.clicks.atmmachine.model.Account;

import java.time.LocalDateTime;

public record StatementRequest(Account account, LocalDateTime start, LocalDateTime end) {
}
