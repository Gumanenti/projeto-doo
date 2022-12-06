package org.example.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.application.view.WindowLoader;

import java.io.File;
import java.io.IOException;

public class MainUI {

    @FXML
    private void initialize(){
    }

    public void showAdminstrator(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("AdministradorManagementUI");
    }

    public void showCertificado(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("CertificadoManagementUI");

    }

    public void showEvento(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("EventoManagementUI");

    }

    public void showParticipante(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("ParticipanteManagementUI");

    }
}
