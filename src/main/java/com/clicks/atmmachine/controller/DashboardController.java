package com.clicks.atmmachine.controller;

import com.clicks.atmmachine.Main;
import com.clicks.atmmachine.utils.StageSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.control.Alert.AlertType.CONFIRMATION;
import static javafx.stage.Modality.WINDOW_MODAL;

public class DashboardController extends Controller implements Initializable {

    @FXML
    private Button balanceBtn;

    @FXML
    private Button cancel;

    @FXML
    private Button changePinBtn;

    @FXML
    private Label nameField;

    @FXML
    private Button statementBtn;

    @FXML
    private BorderPane stdPageContainer;

    @FXML
    private Button transferBtn;

    @FXML
    private Button withdrawBtn;

    /**
     * Function to end a current session
     * it also logs out the ATM Card
     *
     * @param event
     */
    @FXML
    void cancelSession(ActionEvent event) {

        new Alert(CONFIRMATION, "Cancel Transaction?").showAndWait().ifPresent(buttonType -> {
            if (buttonType.equals(ButtonType.OK)){
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("cardEnter.fxml"));
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

                //Destroy all entities peculiar to the authenticated account
                destroyEntities();

                //Goto to Card page
                StageSwitcher.toEnterPage(loader, stage);
            }
            else event.consume();
        });
    }

    /**
     * Handler for PIN update button
     */
    @FXML
    void changePin(ActionEvent event) {

        //Todo: show update PIN page
        StageSwitcher.toWindow(event, new UpdatePinController(), "updatePin.fxml", "Update PIN");
    }

    /**
     * Handler for check balance button
     */
    @FXML
    void checkBalance(ActionEvent event) {
        StageSwitcher.toWindow(event, new AccountBalanceController(), "balance.fxml", "Account Balance");
    }

    /**
     * Handler for Transfer button
     */
    @FXML
    void transfer(ActionEvent event) {
        StageSwitcher.toWindow(event, new TransferController(), "transfer.fxml", "Transfer funds");
    }

    /**
     * Handler for View Statement button
     */
    @FXML
    void viewStatement(ActionEvent event) {

        //Create a new stage to select period
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("statementPeriod.fxml"));

        loader.setController(new StatementPeriodController());

        try {

            stage.setScene(new Scene(loader.load()));
            stage.initModality(WINDOW_MODAL);
            stage.initOwner(((Node)event.getSource()).getScene().getWindow());
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Handler for Withdrawal button
     */
    @FXML
    void withdraw(ActionEvent event) {

        StageSwitcher.toWindow(event, new WithdrawalController(), "withdrawal.fxml", "Withdrawal");

    }

    /**
     * Function to nullify all user details
     * as soon as they leave the session
     */
    public void destroyEntities() {
        customer = null;
        account = null;
        atmCard = null;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameField.setText(customer.getName()); //Set customer name
        nameField.setVisible(true); //Make customer name visible
    }
}
