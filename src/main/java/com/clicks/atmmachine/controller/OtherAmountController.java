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

public class OtherAmountController extends Controller implements Initializable {

    private AccountService accountService;

    @FXML
    private Button cancelTransBtn;

    @FXML
    private TextField amount;

    @FXML
    private Label errorMessage;

    @FXML
    private Button keyEightBtn;

    @FXML
    private Button keyFiveBtn;

    @FXML
    private Button keyFourBtn;

    @FXML
    private Button keyNineBtn;

    @FXML
    private Button keyOneBtn;

    @FXML
    private Button keySevenBtn;

    @FXML
    private Button keySixBtn;

    @FXML
    private Button keyThreeBtn;

    @FXML
    private Button keyTwoBtn;

    @FXML
    private Button keyZeroBtn;

    @FXML
    private Button resetBtn;

    @FXML
    private Button withDrawBtn;

    @FXML
    void cancelTrans(ActionEvent event) {
        if(AppUtils.showAlert(CONFIRMATION, "Cancel Transaction?")) {
            StageSwitcher.toWindow(event, new WithdrawalController(), "withdrawal.fxml", "Withdrawal");
        } else event.consume();

    }

    @FXML
    void handleKey(ActionEvent event) {

        if(!errorMessage.getText().equalsIgnoreCase("")) {
            clearErrorMessage();
        }
        amount.appendText(((Button) event.getSource()).getText());

    }

    @FXML
    void reset(ActionEvent event) {
        amount.clear();
    }

    @FXML
    void withDraw(ActionEvent event) {

        double withdrawAmount = Double.parseDouble(amount.getText());

        WithdrawRequest withdrawRequest = new WithdrawRequest(account, withdrawAmount);

        try {
            accountService.withdrawFunds(withdrawRequest);

            if(AppUtils.showAlert(Alert.AlertType.INFORMATION, "Please take your cash")) {

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

        this.accountService = new AccountService();

        amount.textProperty().addListener((observableValue, s, t1) -> {
            if(!String.valueOf(t1.charAt(t1.length()-1)).matches("\\d")) {
                amount.setText(amount.getText().substring(0, amount.getText().length()-2));
            }
        });

    }
}
