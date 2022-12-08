package com.clicks.atmmachine.service;

import com.clicks.atmmachine.dtos.*;
import com.clicks.atmmachine.model.Account;
import com.clicks.atmmachine.model.AtmCard;
import com.clicks.atmmachine.model.Customer;
import com.clicks.atmmachine.model.Transaction;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static com.clicks.atmmachine.enums.TransactionType.*;
import static com.clicks.atmmachine.utils.AccountUtils.TRANSFER_FEE;
import static com.clicks.atmmachine.utils.AccountUtils.WITHDRAWAL_FEE;
import static java.lang.Boolean.TRUE;

/**
 * A core service class that contains
 * all the business logic for the currently logged-in
 * user account
 */
public class AccountService {

    public static Set<Customer> ALL_CUSTOMERS; //A data source for all the customers in this application
    public static List<Transaction> ALL_TRANSACTIONS; //A data source for all the transactions in this application

    /**
     * A function that performs the transfer of
     * funds from currently authenticated account
     * to a destination account
     *
     * @param transferRequest containing all data for this transfer
     */
    public void transfer(TransferRequest transferRequest) {

        Account account = transferRequest.account(); //Get authenticated account
        double amount = transferRequest.amount(); //Get amount to be transferred
        double totalAmount = amount + TRANSFER_FEE; //Include transfer fee

        validate(account, totalAmount); //Check if this account has enough funds to facilitate this transaction

        Account toAccount = findAccountByAccountNumber(transferRequest.toAccountNumber()); //Get destination account
        account.setCurrentBalance(account.getCurrentBalance() - totalAmount); //Debit authenticated account
        toAccount.setCurrentBalance(toAccount.getCurrentBalance() + amount); //Credit destination account

        //Save transaction for form-account
        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                TRANSFER,
                amount,
                TRANSFER_FEE,
                account,
                TRUE,
                LocalDateTime.now());

        //Save transaction for to-account
        Transaction toTransaction = new Transaction(
                UUID.randomUUID().toString(),
                DEPOSIT,
                amount,
                0.0,
                toAccount,
                TRUE,
                LocalDateTime.now());

        //Todo: Save transaction
        ALL_TRANSACTIONS.add(transaction);
        ALL_TRANSACTIONS.add(toTransaction);
    }

    /**
     * Function to facilitate withdrawal of func
     * from authenticated account
     *
     * @param withdrawRequest contains all details for this transaction
     */
    public void withdrawFunds(WithdrawRequest withdrawRequest) {

        double totalAmount = withdrawRequest.amount() + WITHDRAWAL_FEE; // amount to be debited
        Account thisAccount = withdrawRequest.account(); //Authenticated account

        validate(thisAccount, totalAmount); //Check if this account has enough funds to facilitate this transaction

        thisAccount.setCurrentBalance(thisAccount.getCurrentBalance() - totalAmount);  //Debit this account

        //Save transaction
        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                WITHDRAWAL,
                withdrawRequest.amount(),
                TRANSFER_FEE,
                thisAccount,
                TRUE,
                LocalDateTime.now());

        //Todo: Save transaction
        ALL_TRANSACTIONS.add(transaction);
    }

    /**
     * Function to get statement of account for the
     * current authenticated account
     *
     * @param statementRequest contains all details for statement retrieval
     * @return an instance of {@link List<Transaction>} containing all retrieved transactions
     */

    public List<Transaction> getAccountStatement(StatementRequest statementRequest) {

        LocalDateTime start = statementRequest.start(); //Get the retrieval start date
        LocalDateTime end = statementRequest.end(); //Get the retrieval end date

        String accountNumber = statementRequest.account().getAccountNumber();

        return (Optional.ofNullable(start).isEmpty() || Optional.ofNullable(end).isEmpty()) ? // Checks if start or end date is defined
                ALL_TRANSACTIONS.stream()
                        .filter(transaction -> transaction.getAccount().getAccountNumber().equalsIgnoreCase(accountNumber))
                        .collect(Collectors.toList()) :
                ALL_TRANSACTIONS.stream()
                        .filter(transaction ->
                                transaction.getAccount().getAccountNumber().equalsIgnoreCase(accountNumber) &&
                                        transaction.getCreatedAt().isAfter(ChronoLocalDateTime.from(start.minus(1, ChronoUnit.DAYS))) &&
                                        transaction.getCreatedAt().isBefore(ChronoLocalDateTime.from(end.plus(1, ChronoUnit.DAYS))))
                        .collect(Collectors.toList());
    }

    /**
     * Function to update the PIN of the current ATM card
     *
     * @param updatePinRequest contains details for this PIN update
     * @throws IllegalAccessException when provided old PIN does not match authenticated ATM PIN
     */
    public void updatePin(UpdatePinRequest updatePinRequest) throws IllegalAccessException {

        AtmCard atmCard = updatePinRequest.atmCard(); //Get the current ATM card
        boolean validRequest = Objects.equals(updatePinRequest.oldPin(), atmCard.getPin());

        //Checks if provided old PIN is equal to the old PIN of this ATM Card
        if (validRequest) {
            atmCard.setPin(updatePinRequest.newPin());
        }
        else throw new IllegalAccessException("Invalid old PIN");
    }

    /**
     * Function to find an account using account number
     *
     * @param accountNumber the provided account number
     * @return an instance of {@link Account}
     */
    public Account findAccountByAccountNumber(String accountNumber) {

        return ALL_CUSTOMERS.stream()
                .flatMap(customer -> customer.getAccounts().stream())
                .filter(account1 -> account1.getAccountNumber().equalsIgnoreCase(accountNumber))
                .findFirst()
                .orElseThrow(() -> new InputMismatchException("Invalid Account number " + accountNumber));
    }

    private void validate(Account account, double totalAmount) {
        if(account.getCurrentBalance() - totalAmount < 1000) {
            throw new InputMismatchException("Insufficient balance");
        }
    }
}
