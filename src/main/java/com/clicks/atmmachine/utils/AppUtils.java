package com.clicks.atmmachine.utils;

import javafx.scene.control.Alert;

public class AppUtils {

    /**
     * Function to show user some form of dialog
     * upon certain operation
     *
     * @param alertType the type of alert to be shown
     * @param message the message to be shown
     * @return {@link Boolean} whether or not it is a show and wait type of alert
     */
    public static boolean showAlert(Alert.AlertType alertType, String message) {
        return new Alert(alertType, message).showAndWait().isPresent();
    }
}
