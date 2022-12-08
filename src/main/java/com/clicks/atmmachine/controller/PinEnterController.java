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

/**
 * Controller class for the PIN enter page
 */
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

    /**
     * Handler method to attempt authenticating
     * this inserted ATM Card
     *
     * @param event passed from the button o the view
     */
    @FXML
    void enter(ActionEvent event) {

        String pinText = cardPIN.getText(); //Get the PIN from input field

        boolean validPin = Objects.equals(pinText, atmCard.getPin()); //Validate PIN

        //Check last number of attempted logins
        //this is necessary to avoid brute fox attack
        int noOfLastIncorrectAttempt = atmCard.getNoOfLastIncorrectAttempt();

        //Checks if this PIN is not valid
        if(!validPin) {
            //Display error message
            invalidPIN.setText("Incorrect PIN Trial remains " + (2 - noOfLastIncorrectAttempt));
            invalidPIN.setVisible(true);
            atmCard.setNoOfLastIncorrectAttempt(noOfLastIncorrectAttempt + 1);
        }

        //If attempt exceeds 3 times, User is logged out
        if(noOfLastIncorrectAttempt > 1) {

            destroyEntities();

            //Todo: go to Start Page
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("cardEnter.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            StageSwitcher.toEnterPage(fxmlLoader, stage);
        }

        //If PIN is valid
        if(validPin && noOfLastIncorrectAttempt <= 1) {

            //Todo: Go to dashboard
            StageSwitcher.toWindow(event, new DashboardController(), "dashboard.fxml", "Dashboard");

        }
    }


    private void setWelcomeMessage() {
        welcomeLabel.setText("Welcome " + customer.getName());
    }

    /**
     * Function to handle key inputs
     * from on-screen keyboard in this application
     *
     * @param event
     */
    @FXML
    void handleKey(ActionEvent event) {
        cardPIN.appendText(((Button)event.getSource()).getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Set welcome message
        setWelcomeMessage();

        //Restrict user to enter only 4-digits
        //Ensure that only numeric inputs are made
        cardPIN.textProperty().addListener((observableValue, s, t1) -> {

            String cardPin = cardPIN.getText();

            //Check if input is exceeding 4 digits
            if(t1.length() > 4) {
                cardPIN.setText(cardPin.substring(0, 4));
            }

            //Check for non-numeric inputs
            if(!String.valueOf(t1.charAt(t1.length()-1)).matches("\\d")) {
                cardPIN.setText(cardPin.substring(0, cardPin.length()-2));
            }
        });

        invalidPIN.setVisible(false);
    }

    /**
     * Function to destroy all entities
     * for this customer
     */
    public void destroyEntities() {
        customer = null;
        account = null;
        atmCard = null;
    }
}
