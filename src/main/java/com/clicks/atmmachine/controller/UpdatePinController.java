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

    @FXML
    void cancel(ActionEvent event) {

        new Alert(Alert.AlertType.CONFIRMATION, "Cancel Update").showAndWait().ifPresent(buttonType -> {
            if(buttonType.equals(ButtonType.OK)) {
                StageSwitcher.toWindow(event, new DashboardController(), "dashboard.fxml", "Dashboard");
            }
            else event.consume();
        });

    }

    @FXML
    void savePin(ActionEvent event) {

        String newPinText = newPin.getText().trim();
        String confirmNewPinText = confirmPin.getText().trim();
        String oltPinText = oldPin.getText().trim();

        try {

            if(newPinText.length() < 4) {
                throw new InputMismatchException("New PIN must be 4 digits");
            }

            if(!Objects.equals(newPinText, confirmNewPinText)) {
                throw new InputMismatchException("Confirm PIN must match new PIN");
            }

            accountService.updatePin(new UpdatePinRequest(atmCard, oltPinText, newPinText));

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
                    if(t1.length() > 4) {
                        field.setText(field.getText().substring(0,4));
                    }
                }));

        this.accountService = new AccountService();

    }

}
