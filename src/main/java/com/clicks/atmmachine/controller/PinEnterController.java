package com.clicks.atmmachine.controller;

import com.clicks.atmmachine.Main;
import com.clicks.atmmachine.model.Account;
import com.clicks.atmmachine.model.AtmCard;
import com.clicks.atmmachine.model.Customer;
import com.clicks.atmmachine.utils.StageSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class PinEnterController extends Controller
        implements Initializable {

    @FXML
    private PasswordField cardPIN;

    @FXML
    private Button clearBtn;

    @FXML
    private Button enterBtn;

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
    private Label welcomeLabel;

    @FXML
    private Label invalidPIN;

    @FXML
    void clear(ActionEvent event) {
        cardPIN.clear();
    }

    @FXML
    void enter(ActionEvent event) {

        String pinText = cardPIN.getText();
        boolean validPin = Objects.equals(pinText, atmCard.getPin());

        int noOfLastIncorrectAttempt = atmCard.getNoOfLastIncorrectAttempt();

        if(!validPin) {
            invalidPIN.setText("Incorrect PIN Trial remains " + (2 - noOfLastIncorrectAttempt));
            invalidPIN.setVisible(true);
            atmCard.setNoOfLastIncorrectAttempt(noOfLastIncorrectAttempt + 1);
        }

        if(noOfLastIncorrectAttempt > 1) {

            destroyEntities();

            //Todo: go to Start Page
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("cardEnter.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            StageSwitcher.toEnterPage(fxmlLoader, stage);
        }

        if(validPin && noOfLastIncorrectAttempt <= 1) {

            //Todo: Go to dashboard
            StageSwitcher.toWindow(event, new DashboardController(), "dashboard.fxml", "Dashboard");

        }
    }

    private void setWelcomeMessage() {
        welcomeLabel.setText("Welcome " + customer.getName());
    }

    @FXML
    void handleKey(ActionEvent event) {
        cardPIN.appendText(((Button)event.getSource()).getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cardPIN.setCursor(Cursor.DISAPPEAR);
        cardPIN.textProperty().addListener((observableValue, s, t1) -> {
            if(t1.length() > 4) {
                cardPIN.setText(cardPIN.getText().substring(0, 4));
            }
        });

        invalidPIN.setVisible(false);
    }



    public void setUpParams(Customer customer, Account account, AtmCard atmCard) {
        System.out.println("atmCard = " + PinEnterController.atmCard);

        setWelcomeMessage();
    }

    public void destroyEntities() {
        customer = null;
        account = null;
        atmCard = null;
    }
}
