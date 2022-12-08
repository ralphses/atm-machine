package com.clicks.atmmachine.utils;

import javafx.scene.control.Alert;

public class AppUtils {

    public static boolean showAlert(Alert.AlertType alertType, String message) {
        return new Alert(alertType, message).showAndWait().isPresent();
    }
}
