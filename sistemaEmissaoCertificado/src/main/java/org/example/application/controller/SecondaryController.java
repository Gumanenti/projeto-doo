package org.example.application.controller;

import javafx.fxml.FXML;
import org.example.application.view.WindowLoader;

import java.io.IOException;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        WindowLoader.setRoot("primary");
    }
}