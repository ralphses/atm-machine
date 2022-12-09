package com.clicks.atmmachine.controller;

import com.clicks.atmmachine.model.Account;
import com.clicks.atmmachine.model.AtmCard;
import com.clicks.atmmachine.model.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Super class for all controllers
 * in this application
 */
public class Controller {

    public static Customer customer;
    public static Account account;
    public static AtmCard atmCard;

    @FXML
    protected Label errorMessage;

    /**
     * Function to clear error message
     */
    protected void clearErrorMessage() {
        errorMessage.setText(""); //set the error message to empty string
        errorMessage.setVisible(false); //make error message invisible
    }

    /**
     * Function to show error message
     * @param message message to be shown
     */
    protected void displayErrorMessage(String message) {
        errorMessage.setText(message);
        errorMessage.setVisible(true);
    }



}
