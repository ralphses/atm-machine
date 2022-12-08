package com.clicks.atmmachine.controller;

import com.clicks.atmmachine.model.Transaction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class StatementController extends Controller implements Initializable {

    private List<Transaction> transactions;

    @FXML
    private Label accountName;

    @FXML
    private Button backToHomeBtn;

    @FXML
    private Label period;

    @FXML
    private HBox pageHolder;

    @FXML
    private ListView<String> statementSpace;

    @FXML
    void backToHome(ActionEvent event) {
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        statementSpace.getItems().add("ID\t\t\t\tType\t\t\t\tAmount\t\t\tFee\t\tDestination\t\t\tCompleted\t\t\tTime");

        transactions.forEach(transaction -> statementSpace.getItems().add(transaction.toString()));
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
