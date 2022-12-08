package com.clicks.atmmachine.controller;

import com.clicks.atmmachine.dtos.UpdatePinRequest;
import com.clicks.atmmachine.service.AccountService;
import com.clicks.atmmachine.utils.StageSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Stream;

/**
 * A controller class for the update PIN View
 */
public class UpdatePinController extends Controller implements Initializable {

    private AccountService accountService;

    @FXML
    private Button cancelButton;

    @FXML
    private PasswordField oldPin;

    @FXML
    private PasswordField confirmPin;

    @FXML
    private Label errorMessage;

    @FXML
    private PasswordField newPin;

    @FXML
    private Button savePinBtn;

    /**
     * Function to cancel
     * a particular PIN update attempt
     *
     * @param event
     */
    @FXML
    void cancel(ActionEvent event) {

        //Alert user if they really want to cancel this transaction
        new Alert(Alert.AlertType.CONFIRMATION, "Cancel Update").showAndWait().ifPresent(buttonType -> {
            if(buttonType.equals(ButtonType.OK)) {

                //Go back to Dashboard
                StageSwitcher.toWindow(event, new DashboardController(), "dashboard.fxml", "Dashboard");
            }
            else event.consume();
        });

    }

    /**
     * Function to save a new PIN
     *
     * @param event
     */
    @FXML
    void savePin(ActionEvent event) {

        //Get the new PIN
        String newPinText = newPin.getText().trim();

        //Get the confirmed new PIN
        String confirmNewPinText = confirmPin.getText().trim();

        //Get the old PIN
        String oltPinText = oldPin.getText().trim();

        //Attempt to Save the new PIN
        try {

            //Check that PIN is not more than 4 digits
            if(newPinText.length() < 4) {
                throw new InputMismatchException("New PIN must be 4 digits");
            }

            //Check that PIN matches confirm PIN
            if(!Objects.equals(newPinText, confirmNewPinText)) {
                throw new InputMismatchException("Confirm PIN must match new PIN");
            }

            //Attempt PIN update
            accountService.updatePin(new UpdatePinRequest(atmCard, oltPinText, newPinText));

            //Alert user that PIN Update is successful
            new Alert(Alert.AlertType.INFORMATION, "PIN Updated successfully!")
                    .showAndWait()
                    .ifPresent(buttonType -> {
                        if(buttonType.equals(ButtonType.OK)) {
                            StageSwitcher.toWindow(event, new DashboardController(), "dashboard.fxml", "Dashboard");
                        }
                    });

        } catch (IllegalAccessException | InputMismatchException e) {
            errorMessage.setText(e.getMessage());
            errorMessage.setVisible(true);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Stream.of(newPin, confirmPin)
                .forEach(field -> field.textProperty().addListener((observableValue, s, t1) -> {

                    String fieldText = field.getText();

                    if(t1.length() > 4) {
                        field.setText(fieldText.substring(0,4));
                    }

                    //Check for non-numeric inputs
                    if(!String.valueOf(t1.charAt(t1.length()-1)).matches("\\d")) {
                        field.setText(fieldText.substring(0, fieldText.length()-2));
                    }
                }));

        this.accountService = new AccountService();

    }

}
