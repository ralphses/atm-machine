package com.clicks.atmmachine.controller;

import com.clicks.atmmachine.model.Transaction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Class to view statement of
 * Account for this user
 */
public class StatementController extends Controller implements Initializable {

    //List of transactions
    private List<Transaction> transactions;


    @FXML
    private Button backToHomeBtn;

    @FXML
    private HBox pageHolder;

    @FXML
    private ListView<String> statementSpace;

    /**
     * Function to close the statement view
     */
    @FXML
    void backToHome(ActionEvent event) {
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }

    /**
     * Sets up all the components of this
     * view statement
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Prepare the header
        statementSpace.getItems().add("ID\t\t\t\tType\t\t\t\tAmount\t\t\tFee\t\tDestination\t\t\tCompleted\t\t\tTime");

        //Load the transactions into the transaction view
        transactions.forEach(transaction -> statementSpace.getItems().add(transaction.toString()));
    }

    /**
     * Function to initialize this statement transaction
     *
     * @param transactions the transactions
     */
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

}
