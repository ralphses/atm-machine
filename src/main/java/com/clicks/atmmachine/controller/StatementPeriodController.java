package com.clicks.atmmachine.controller;

import com.clicks.atmmachine.dtos.StatementRequest;
import com.clicks.atmmachine.model.Transaction;
import com.clicks.atmmachine.service.AccountService;
import com.clicks.atmmachine.utils.StageSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import static java.time.LocalTime.now;

public class StatementPeriodController extends Controller implements Initializable {

    private AccountService accountService;

    private Stage stage;

    @FXML
    private Button cancelBtn;

    @FXML
    private DatePicker endDate;

    @FXML
    private DatePicker startDate;

    @FXML
    private Button viewBtn;

    @FXML
    void cancel(ActionEvent event) {
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    void view(ActionEvent event) {
        System.out.println("sta = " + startDate);

        LocalDateTime startDateValue = LocalDateTime.of(startDate.getValue(), now());
        LocalDateTime endDateValue = LocalDateTime.of(endDate.getValue(), now());

        List<Transaction> transactions = accountService.getAccountStatement(new StatementRequest(account, startDateValue, endDateValue));

        StatementController statementController = new StatementController();
        statementController.setTransactions(transactions);

        StageSwitcher.toWindow(event, statementController, "accountStatement.fxml", "Account Statement");



    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.accountService = new AccountService();

        endDate.setValue(LocalDate.now());
        startDate.setValue(LocalDate.now());
    }
}
