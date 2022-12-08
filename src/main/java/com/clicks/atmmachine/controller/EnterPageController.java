package com.clicks.atmmachine.controller;

import com.clicks.atmmachine.Main;
import com.clicks.atmmachine.utils.AccountUtils;
import com.clicks.atmmachine.utils.StageSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.InputMismatchException;
import java.util.ResourceBundle;

/**
 * Controller page for Welcome page window
 */
public class EnterPageController  extends Controller  implements Initializable {

    private AccountUtils accountUtils;

    @FXML
    private TextField cardNumber;

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

    /**
     * Function to clear the
     * input field
     *
     * @param event
     */
    @FXML
    void clear(ActionEvent event) {
        cardNumber.clear(); //clear input field
        clearErrorMessage(); //clear error message if any
    }

    /**
     * Function to log in an ATM card into
     * the system
     *
     * @param event
     */
    @FXML
    void enter(ActionEvent event) {

        String atmCardNumber = cardNumber.getText().trim(); //Get user input

        try {
            //find this customer using ATM card number
            customer = accountUtils.findCustomer(atmCardNumber);

            //Validate if this ATM is not locked
            boolean accountLocked = customer.getAccounts()
                    .stream()
                    .anyMatch(account -> account.getAtmCards()
                            .stream()
                            .anyMatch(atmCard -> atmCard.getCardNumber().equalsIgnoreCase(atmCardNumber) && atmCard.isLocked()));

            //Notify the user that his account is locked
            if(accountLocked) throw new IllegalAccessException("Account Locked! Contact your bank");

            account = accountUtils.findCurrentAccount(atmCardNumber, customer); //Find this account
            atmCard = accountUtils.findCurrentAtmCard(atmCardNumber, account); //Find this ATM card object

            //Todo: go to PIN Page
            URL resource = Main.class.getResource("pinEnter.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(resource);

            StageSwitcher.toCardPinPage(fxmlLoader, event, customer, account, atmCard);


        }catch (InputMismatchException exception) {
            displayErrorMessage("Invalid Card Number " + atmCardNumber); //When invalid card is entered

        } catch (IllegalAccessException e) {
            displayErrorMessage(e.getMessage());
        }
    }


    /**
     * Function to handle key enteries
     * using on-screen keyboard
     *
     * @param event
     */
    @FXML
    void handleKey(ActionEvent event) {
        //Clear error message if existing
        if(!errorMessage.getText().equalsIgnoreCase("")) {
            clearErrorMessage();
        }
        //Get input to input field
        cardNumber.appendText(((Button) event.getSource()).getText());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorMessage.setVisible(false);
        this.accountUtils = new AccountUtils();

        //Make sure only numbers are entered in the input field
        cardNumber.textProperty().addListener((observableValue, s, t1) -> {
            if(!String.valueOf(t1.charAt(t1.length()-1)).matches("\\d")) {
                cardNumber.setText(cardNumber.getText().substring(0, cardNumber.getText().length()-1));
            }
        });
    }

}
