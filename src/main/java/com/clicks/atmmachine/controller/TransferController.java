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
 * controller class for Transfer window
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

    @FXML
    void cancelTransfer(ActionEvent event) {

        new Alert(Alert.AlertType.CONFIRMATION, "Cancel Transfer?").showAndWait().ifPresent(buttonType -> {
            if (buttonType.equals(ButtonType.OK)) {
                StageSwitcher.toWindow(event, new DashboardController(), "dashboard.fxml", "Dashboard");
            }
            else event.consume();
        });
    }

    @FXML
    void transfer(ActionEvent event) {

        String accountNum = accountNumber.getText().trim();

        try {
            if(accountName.getText().trim().length() < 1){

                Account acc = accountService.findAccountByAccountNumber(accountNum);
                accountName.setText(new AccountUtils().findCustomerByNumber(accountNum).getName());

                transferBtn.setText("Confirm");

            }
            else {

                double transferAmount = Double.parseDouble(amount.getText().trim());
                TransferRequest transferRequest = new TransferRequest(account, accountNum, transferAmount);

                accountService.transfer(transferRequest);

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
