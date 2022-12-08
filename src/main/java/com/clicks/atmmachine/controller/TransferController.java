package com.clicks.atmmachine.controller;

import com.clicks.atmmachine.dtos.TransferRequest;
import com.clicks.atmmachine.model.Account;
import com.clicks.atmmachine.service.AccountService;
import com.clicks.atmmachine.utils.AccountUtils;
import com.clicks.atmmachine.utils.StageSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.InputMismatchException;
import java.util.ResourceBundle;

/**
 * Controller class for Transfer window
 */
public class TransferController extends Controller implements Initializable {

    private AccountService accountService;

    @FXML
    private TextField accountName;

    @FXML
    private TextField accountNumber;

    @FXML
    private TextField amount;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button transferBtn;

    @FXML
    private Label welcomeLabel;

    /**
     * Function to cancel
     * a particular transfer attempt
     *
     * @param event
     */
    @FXML
    void cancelTransfer(ActionEvent event) {

        //Ask whether to cancel or not
        new Alert(Alert.AlertType.CONFIRMATION, "Cancel Transfer?").showAndWait().ifPresent(buttonType -> {
            if (buttonType.equals(ButtonType.OK)) {

                //Switch the dashboard
                StageSwitcher.toWindow(event, new DashboardController(), "dashboard.fxml", "Dashboard");
            }
            else event.consume();
        });
    }

    /**
     * Function to attempt transfer of funds
     * from this account
     *
     * @param event
     */
    @FXML
    void transfer(ActionEvent event) {

        //Get the destination account number
        String accountNum = accountNumber.getText().trim();

        try {

            //Check if account name is not set
            if(accountName.getText().trim().length() < 1){

                //Find account using account number
                Account acc = accountService.findAccountByAccountNumber(accountNum);

                //Set account number
                accountName.setText(new AccountUtils().findCustomerByNumber(accountNum).getName());

                //Change transfer button text to Confirm
                transferBtn.setText("Confirm");

            }

            //Perform the transfer
            else {

                //Get the amount to be transferred
                double transferAmount = Double.parseDouble(amount.getText().trim());

                //Create a transfer request
                TransferRequest transferRequest = new TransferRequest(account, accountNum, transferAmount);

                //Attempt Transfer
                accountService.transfer(transferRequest);

                //Alert USer that transfer is successful
                new Alert(Alert.AlertType.INFORMATION, "Transfer Successful!").showAndWait().ifPresent(buttonType -> {
                    if(buttonType.equals(ButtonType.OK)) {
                        StageSwitcher.toWindow(event, new DashboardController(), "dashboard.fxml", "Dashboard");
                    }
                    else event.consume();
                });
            }

        }catch (InputMismatchException e) {
            errorMessage.setText(e.getMessage());
            errorMessage.setVisible(true);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.accountService = new AccountService();
        accountName.setEditable(false);
    }
}
