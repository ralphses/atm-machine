package com.clicks.atmmachine.utils;

import com.clicks.atmmachine.model.Account;
import com.clicks.atmmachine.model.AtmCard;
import com.clicks.atmmachine.model.Customer;

import java.util.InputMismatchException;

import static com.clicks.atmmachine.service.AccountService.*;

public class AccountUtils {

    public static final double TRANSFER_FEE = 20.5;
    public static final double WITHDRAWAL_FEE = 5.5;

    public Customer findCustomerByNumber(String accountNumber) {
        return ALL_CUSTOMERS.stream()
                .filter(customer -> customer.getAccounts()
                        .stream()
                        .anyMatch(account -> account.getAccountNumber().equalsIgnoreCase(accountNumber)))
                .findFirst()
                .orElseThrow(() -> new InputMismatchException("Invalid card number "));
    }

    public Customer findCustomer(String cardNumber) {

       return ALL_CUSTOMERS.stream()
               .filter(customer -> customer.getAccounts()
                       .stream()
                       .anyMatch(account -> account.getAtmCards()
                               .stream()
                               .anyMatch(atmCard -> atmCard.getCardNumber().equalsIgnoreCase(cardNumber))))
               .findFirst()
               .orElseThrow(() -> new InputMismatchException("Invalid card number "));
    }

    public AtmCard findCurrentAtmCard(String cardNumber, Account account) {

        return account.getAtmCards()
                .stream()
                .flatMap(atmCard -> account.getAtmCards().stream())
                .filter(atmCard -> atmCard.getCardNumber().equalsIgnoreCase(cardNumber))
                .findFirst()
                .orElseThrow(() -> new InputMismatchException("Invalid card number"));
    }

    public Account findCurrentAccount(String cardNumber, Customer customer) {

        return customer.getAccounts()
                .stream()
                .filter(account -> account.getAtmCards().contains(findCurrentAtmCard(cardNumber, account)))
                .findFirst()
                .orElseThrow(() -> new InputMismatchException("Invalid card number"));
    }
}
