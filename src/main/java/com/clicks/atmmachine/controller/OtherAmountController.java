package com.clicks.atmmachine.controller;

import com.clicks.atmmachine.dtos.WithdrawRequest;
import com.clicks.atmmachine.service.AccountService;
import com.clicks.atmmachine.utils.AppUtils;
import com.clicks.atmmachine.utils.StageSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.InputMismatchException;
import java.util.ResourceBundle;

import static javafx.scene.control.Alert.AlertType.CONFIRMATION;

/**
 * A Class that controls the view
 * for withdrawing amount not listed
 * on the amount page
 */
public class OtherAmountController extends Controller implements Initializable {

    private AccountService accountService;

    @FXML
    private Button cancelTransBtn;

    @FXML
    private TextField amount;

    @FXML
    private Label errorMessage;

    @FXML
    private Button withDrawBtn;

    /**
     * Function to cancel
     * a particular withdrawal attempt
     *
     * @param event
     */
    @FXML
    void cancelTrans(ActionEvent event) {
        //Prompt the user where to really cancel the
        //transaction or not
        if(AppUtils.showAlert(CONFIRMATION, "Cancel Transaction?")) {
            //Switch to the main withdrawal page
            StageSwitcher.toWindow(event, new WithdrawalController(), "withdrawal.fxml", "Withdrawal");
        } else event.consume();

    }

    /**
     * Function to handle key inputs
     * from on-screen keyboard in this application
     *
     * @param event
     */
    @FXML
    void handleKey(ActionEvent event) {

        //Check if there is an error message
        if(!errorMessage.getText().equalsIgnoreCase("")) {
            clearErrorMessage();
        }

        //Add the clicked button value to the input field
        amount.appendText(((Button) event.getSource()).getText());

    }

    /**
     * Function to clear input field
     *
     * @param event
     */
    @FXML
    void reset(ActionEvent event) {
        amount.clear();
    }

    /**
     * Function to facilitate the withdrawal
     * of funds from this account
     *
     * @param event
     */
    @FXML
    void withDraw(ActionEvent event) {

        //Get amount to be withdrawn from the input field
        double withdrawAmount = Double.parseDouble(amount.getText());

        //Create a new fund withdrawal request to be passed to the AccountService
        WithdrawRequest withdrawRequest = new WithdrawRequest(account, withdrawAmount);

        try {
            //Attempt withdrawal
            accountService.withdrawFunds(withdrawRequest);

            //Show alert if withdrawal is successful
            if(AppUtils.showAlert(Alert.AlertType.INFORMATION, "Please take your cash")) {

                //move to the dashboard
                StageSwitcher.toWindow(event, new DashboardController(), "dashboard.fxml", "Dashboard");
            }
            else event.consume();

        }catch (InputMismatchException e) {
            errorMessage.setText(e.getMessage());
            errorMessage.setVisible(true);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.accountService = new AccountService(); //Initialize the accountService Object

        //Ensure that only numeric values are added into the input field
        amount.textProperty().addListener((observableValue, s, t1) -> {
            if(!String.valueOf(t1.charAt(t1.length()-1)).matches("\\d")) {
                amount.setText(amount.getText().substring(0, amount.getText().length()-2));
            }
        });

    }
}
