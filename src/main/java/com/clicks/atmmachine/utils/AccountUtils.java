package com.clicks.atmmachine.utils;

import com.clicks.atmmachine.model.Account;
import com.clicks.atmmachine.model.AtmCard;
import com.clicks.atmmachine.model.Customer;

import java.util.InputMismatchException;

import static com.clicks.atmmachine.service.AccountService.*;

/**
 * Util class for Customer account
 */
public class AccountUtils {

    public static final double TRANSFER_FEE = 20.5; //Transfer fee for all users
    public static final double WITHDRAWAL_FEE = 5.5; //Withdrawal fee for all users

    /**
     * Find {@link Customer} with provided account number
     *
     * @param accountNumber provided account number
     * @return an instance of {@link Customer}
     */
    public Customer findCustomerByNumber(String accountNumber) {
        return ALL_CUSTOMERS.stream()
                .filter(customer -> customer.getAccounts()
                        .stream()
                        .anyMatch(account -> account.getAccountNumber().equalsIgnoreCase(accountNumber)))
                .findFirst()
                //if no such customer is found, an exception is thrown
                .orElseThrow(() -> new InputMismatchException("Invalid card number "));
    }

    /**
     * Find {@link Customer} with provided ATM card number
     *
     * @param cardNumber provided ATM card number
     * @return an instance of {@link Customer}
     */
    public Customer findCustomer(String cardNumber) {

       return ALL_CUSTOMERS.stream()
               .filter(customer -> customer.getAccounts()
                       .stream()
                       .anyMatch(account -> account.getAtmCards()
                               .stream()
                               .anyMatch(atmCard -> atmCard.getCardNumber().equalsIgnoreCase(cardNumber))))
               .findFirst()
               //if no such customer is found, an exception is thrown
               .orElseThrow(() -> new InputMismatchException("Invalid card number "));
    }

    /**
     * Function to find an ATM Card for this account
     *
     * @param cardNumber the ATM card number
     * @param account the Account
     * @return an instance of {@link AtmCard}
     *
     * @throws InputMismatchException when card number is invalid
     */
    public AtmCard findCurrentAtmCard(String cardNumber, Account account) {

        return account.getAtmCards()
                .stream()
                .flatMap(atmCard -> account.getAtmCards().stream())
                .filter(atmCard -> atmCard.getCardNumber().equalsIgnoreCase(cardNumber))
                .findFirst()
                //if no such ATM Card is found, an exception is thrown
                .orElseThrow(() -> new InputMismatchException("Invalid card number"));
    }

    /**
     * Function to find a particular {@link Account}
     *
     * @param cardNumber provided card number
     * @param customer the provided customer
     * @return an instance of {@link Account}
     */
    public Account findCurrentAccount(String cardNumber, Customer customer) {

        return customer.getAccounts()
                .stream()
                .filter(account -> account.getAtmCards().contains(findCurrentAtmCard(cardNumber, account)))
                .findFirst()
                .orElseThrow(() -> new InputMismatchException("Invalid card number"));
    }
}
