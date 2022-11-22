package org.example.application.controller;

import javafx.fxml.FXML;
import org.example.application.view.App;

import java.io.IOException;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}