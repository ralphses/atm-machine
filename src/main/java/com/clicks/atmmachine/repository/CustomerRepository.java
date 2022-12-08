package com.clicks.atmmachine.repository;

import com.clicks.atmmachine.model.Account;
import com.clicks.atmmachine.model.AtmCard;
import com.clicks.atmmachine.model.Customer;

import java.time.LocalDate;
import java.util.Set;

import static com.clicks.atmmachine.enums.AccountType.*;
import static com.clicks.atmmachine.enums.CardType.*;

/**
 * Data source for this application
 */
public class CustomerRepository {

    private Customer firstCustomer;
    private Customer secondCustomer;

    public CustomerRepository() {

        //Initialize this repository
        initializeRepository();
    }

    private void initializeRepository() {

        LocalDate now = LocalDate.now();

        //Initialize all ATM cards
        AtmCard firstCustomerAtm = new AtmCard("25136078", "0021", false, now.plusYears(2), VISA, false, 0);
        AtmCard secondCustomerAtm = new AtmCard("81502135", "2221", false, now.plusYears(1), MASTER, false, 0);

        //Initialize all Accounts
        Account firstCustomerAccount = new Account("1153782501", SAVINGS, 20000.0, Set.of(firstCustomerAtm));
        Account secondCustomerAccount = new Account("2158731011", CURRENT, 10000.0, Set.of(secondCustomerAtm));

        //Assign accounts to customers
        this.firstCustomer = new Customer("Gloria Sunday", "glorysunday@gmail.com", Set.of(firstCustomerAccount));
        this.secondCustomer = new Customer("Ezekiel Onyeoma", "onyeomaeasy@gmail.com", Set.of(secondCustomerAccount));
    }

    public Customer getFirstCustomer() {
        return firstCustomer;
    }

    public Customer getSecondCustomer() {
        return secondCustomer;
    }

    @Override
    public String toString() {
        return "CustomerRepository{" +
                "firstCustomer=" + firstCustomer +
                ", secondCustomer=" + secondCustomer +
                '}';
    }
}
