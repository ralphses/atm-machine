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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.InputMismatchException;
import java.util.ResourceBundle;

import static javafx.scene.control.Alert.AlertType.CONFIRMATION;

/**
 * Controller class for the withdrawal window
 */
public class WithdrawalController extends Controller implements Initializable {

    private AccountService accountService;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button otherAmountBtn;

    @FXML
    private Label welcomeLabel;

    /**
     * Function to cancel the initiated withdrawal
     */
    @FXML
    void cancelWithdrawal(ActionEvent event) {

        //Show a dialog box to confirm cancellation
        new Alert(CONFIRMATION, "Cancel Transaction?").showAndWait().ifPresent(buttonType -> {
            if (buttonType.equals(ButtonType.OK)){
                //Back to dashboard
                StageSwitcher.toWindow(event, new DashboardController(), "dashboard.fxml", "Dashboard");
            }
            else event.consume();
        });


    }

    /**
     * Function to process withdrawal of
     * amounts not provided in the withdrawal window
     */

    @FXML
    void otherAmount(ActionEvent event) {
        //Todo: Show other amount page
        StageSwitcher.toWindow(event, new OtherAmountController(), "otherAmount.fxml", "Other Amount");
    }

    /**
     * Function to facilitate withdrawal of
     * amount found in the withdrawal window
     *
     * @param event
     */
    @FXML
    void regularAmount(ActionEvent event) {
        String text = ((Button) event.getSource()).getText(); //Get the amount from button pressed

        //Todo: prepare a withdrawal request
        WithdrawRequest withdrawRequest = new WithdrawRequest(account, Double.parseDouble(text));

        try {
            //perform withdrawal
            accountService.withdrawFunds(withdrawRequest);

            //Show success message
            if(AppUtils.showAlert(Alert.AlertType.INFORMATION, "Please take your cash")) {

                StageSwitcher.toWindow(event, new DashboardController(), "dashboard.fxml", "Dashboard");
            }
            else event.consume();

        }catch (InputMismatchException e) {
            errorMessage.setText(e.getMessage()); //WHen insufficient balance
            errorMessage.setVisible(true);
        }

    }

    /**
     * Set up all needed objects
     * for this window
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.accountService = new AccountService(); //set account service
    }
}
