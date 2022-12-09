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

/**
 * A controller class for the modal window to set
 * period of transaction for the statement
 */
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

    /**
     * Function to cancel
     * a particular operation
     *
     * @param event
     */
    @FXML
    void cancel(ActionEvent event) {
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }

    /**
     * Function to view the statement of account
     *
     * @param event
     */
    @FXML
    void view(ActionEvent event) {

        LocalDateTime startDateValue = LocalDateTime.of(startDate.getValue(), now()); //Get start date
        LocalDateTime endDateValue = LocalDateTime.of(endDate.getValue(), now()); //GEt end date

        //Fetch transactions for this user between period above
        List<Transaction> transactions =
                accountService.getAccountStatement(new StatementRequest(account, startDateValue, endDateValue));

        //Initialize a controller for the View Statement page
        StatementController statementController = new StatementController();

        //Initialize transactions
        statementController.setTransactions(transactions);

        //Switch to the statement view page
        StageSwitcher.toWindow(event, statementController, "accountStatement.fxml", "Account Statement");

    }


    /**
     * Function to set up this class
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.accountService = new AccountService();

        startDate.setValue(LocalDate.now()); //Default value of start date should be now
        endDate.setValue(LocalDate.now()); //Default value of end date should be now
    }
}
