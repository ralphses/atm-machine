package com.clicks.atmmachine;

import com.clicks.atmmachine.repository.CustomerRepository;
import com.clicks.atmmachine.utils.StageSwitcher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Set;

import static com.clicks.atmmachine.service.AccountService.ALL_CUSTOMERS;
import static com.clicks.atmmachine.service.AccountService.ALL_TRANSACTIONS;

/**
 * An implementation of a
 * Simple ATM with basic functionalities
 * such as withdraw funds and as well Transfer
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage stage){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cardEnter.fxml"));
        StageSwitcher.toEnterPage(fxmlLoader, stage);
    }

    @Override
    public void init() throws Exception {

        CustomerRepository customerRepository = new CustomerRepository();

        ALL_CUSTOMERS = Set.of(customerRepository.getFirstCustomer(), customerRepository.getSecondCustomer());
        ALL_TRANSACTIONS = new ArrayList<>();

        super.init();
    }
}