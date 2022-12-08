package com.clicks.atmmachine.controller;

import com.clicks.atmmachine.utils.StageSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Stream;

/**
 * A controller class for viewing account
 * balance for the authenticated customer
 */
public class AccountBalanceController extends Controller implements Initializable {

    @FXML
    private TextField accountName;

    @FXML
    private TextField accountNumber;

    @FXML
    private Button closeBtn;

    @FXML
    private TextField currentBalance;

    @FXML
    private Label errorMessage;

    @FXML
    private Label welcomeLabel;

    /**
     * Closes the balance window
     *
     * @param event
     */
    @FXML
    void close(ActionEvent event) {
        StageSwitcher.toWindow(event, new DashboardController(), "dashboard.fxml", "Dashboard");
    }

    /**
     * Initializes the account details of this customer
     * and sets the current account balance
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Stream.of(accountName, accountNumber, currentBalance).forEach(textField -> textField.setEditable(false));
        accountName.setText(customer.getName());
        accountNumber.setText(account.getAccountNumber());
        currentBalance.setText(String.valueOf(account.getCurrentBalance()));

    }
}
