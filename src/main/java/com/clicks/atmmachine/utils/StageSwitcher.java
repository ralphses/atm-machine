package com.clicks.atmmachine.utils;

import com.clicks.atmmachine.Main;
import com.clicks.atmmachine.controller.*;
import com.clicks.atmmachine.model.Account;
import com.clicks.atmmachine.model.AtmCard;
import com.clicks.atmmachine.model.Customer;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.control.Alert.AlertType.CONFIRMATION;
import static javafx.scene.control.ButtonType.OK;

/**
 * A key util class for moving from one
 * window to another
 */
public class StageSwitcher {

    /**
     * Function to move from the Enter page to Card PIN page
     *
     * @param loader the view
     * @param event
     * @param customer the customer
     * @param customerAccount the account
     * @param customerAtmCard the ATM card
     */
    public static void toCardPinPage(FXMLLoader loader, Event event, Customer customer, Account customerAccount, AtmCard customerAtmCard) {

        try {
            Parent load = loader.load();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            PinEnterController pinEnterController = loader.getController();

            setWindow(window, load, "Enter PIN");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Function to enter the Entry Page of this application
     *
     * @param loader the loader containing the view
     * @param stage the window
     */
    public static void toEnterPage(FXMLLoader loader, Stage stage) {
        try {
            setWindow(stage, loader.load(), "Insert Card");

            //Prompt the user whether they really want to exit the application or not
            stage.setOnCloseRequest(windowEvent -> new Alert(CONFIRMATION, "Exit Application?")
                    .showAndWait()
                    .ifPresent(present -> {
                        if(present == OK) stage.close();
                        else windowEvent.consume();
                    }));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * Function to switch to any other window
     *
     * @param event an action from the view
     * @param controller the view controller
     * @param view the name of the view file (usually a .fxml file)
     * @param stageTitle the title of the new window
     */
    public static void toWindow(ActionEvent event, Controller controller, String view, String stageTitle) {

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(view)); //load the view
            Stage stage = (Stage) ( (Node) event.getSource()).getScene().getWindow(); //Get teh window from the event

            loader.setController(controller); //set controller

            //Complete window setup
            setWindow(stage, loader.load(), stageTitle);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Function to complete window set up
     *
     * @param stage the window
     * @param root the main node
     * @param stageTitle the title of the new window
     */
    private static void setWindow(Stage stage, Parent root, String stageTitle) {
        Scene scene = new Scene(root, 900, 600);
        stage.setScene(scene);
        stage.setTitle(stageTitle);
        stage.setResizable(false);
        stage.show();
    }

}
