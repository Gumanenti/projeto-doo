package org.example.application.controller;

import javafx.fxml.FXML;
import org.example.application.view.WindowLoader;

import java.io.IOException;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        WindowLoader.setRoot("secondary");
    }
}
