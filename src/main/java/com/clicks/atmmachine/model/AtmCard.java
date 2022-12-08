package com.clicks.atmmachine.model;

import com.clicks.atmmachine.enums.CardType;

import java.time.LocalDate;

public class AtmCard {

    private String cardNumber;
    private String pin;
    private Boolean isExpired;
    private LocalDate expiryDate;
    private CardType cardType;
    private boolean isLocked;
    private int noOfLastIncorrectAttempt;

    public AtmCard(String cardNumber, String pin, Boolean isExpired, LocalDate expiryDate, CardType cardType, boolean isLocked, int noOfLastIncorrectAttempt) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.isExpired = isExpired;
        this.expiryDate = expiryDate;
        this.cardType = cardType;
        this.isLocked = isLocked;
        this.noOfLastIncorrectAttempt = noOfLastIncorrectAttempt;
    }

    public AtmCard() {}

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Boolean getExpired() {
        return isExpired;
    }

    public void setExpired(Boolean expired) {
        isExpired = expired;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    @Override
    public String toString() {
        return "AtmCard{" +
                "cardNumber='" + cardNumber + '\'' +
                ", pin='" + pin + '\'' +
                ", isExpired=" + isExpired +
                ", expiryDate=" + expiryDate +
                ", cardType=" + cardType +
                ", isLocked=" + isLocked +
                '}';
    }

    public int getNoOfLastIncorrectAttempt() {
        return noOfLastIncorrectAttempt;
    }

    public void setNoOfLastIncorrectAttempt(int noOfLastIncorrectAttempt) {
        this.noOfLastIncorrectAttempt = noOfLastIncorrectAttempt;
    }
}
