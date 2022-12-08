package com.clicks.atmmachine.model;

import java.util.Set;

public class Customer {

    private String name;
    private String email;
    private Set<Account> accounts;

    public Customer(String name, String email, Set<Account> accounts) {
        this.name = name;
        this.email = email;
        this.accounts = accounts;
    }

    public Customer() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", accounts=" + accounts +
                '}';
    }
}
