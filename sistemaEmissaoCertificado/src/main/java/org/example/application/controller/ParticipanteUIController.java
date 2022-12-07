package org.example.application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.application.view.WindowLoader;
import org.example.domain.entities.participante.Participante;

import java.io.IOException;

import static org.example.application.main.Main.*;


public class ParticipanteUIController {
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtCPF;
    @FXML
    private Button btnConfirm;
    @FXML
    private Button btnCancel;

    private Participante participante;
    private boolean changeInList = false;

    private void getEntityToView(){
        if(participante == null){
            participante = new Participante();
        }
        participante.setNome(txtNome.getText());
        participante.setEmail(txtEmail.getText());
        participante.setCpf(txtCPF.getText());
    }

    private void setEntityIntoView(){
        txtNome.setText(participante.getNome());
        txtEmail.setText(participante.getEmail());
        txtCPF.setText(participante.getCpf());
    }

    public void saveOrUpdate() throws IOException {
        getEntityToView();
        if (changeInList)
            participanteToGenerateCertificate.add(participante);
        else {
            if (findParticipanteUseCase.findOne(participante.getCpf()).isEmpty()){
                createParticipanteUseCase.insert(participante);
            } else {
                updateParticipanteUseCase.update(participante);
            }
        }
        WindowLoader.setRoot("PreGerarCertificadosUIManagement");
    }

    public void backToPreviewScenne() throws IOException {
        WindowLoader.setRoot("PreGerarCertificadosUIManagement");
    }

    public void setParticipante(Participante selectedItem, UIMode mode) {
        if (selectedItem == null){
            throw new IllegalArgumentException("Participante não pode ser nulo");
        } else {
            this.participante = selectedItem;
            setEntityIntoView();
        }
        if (mode == UIMode.VIEW){
            configureViewMode();
        }
    }

    private void configureViewMode() {
        btnCancel.setLayoutX(btnConfirm.getLayoutX());
        btnCancel.setLayoutX(btnConfirm.getLayoutY());
        btnCancel.setText("Fechar");

        btnConfirm.setVisible(false);
        txtCPF.setDisable(true);
        txtNome.setDisable(true);
        txtEmail.setDisable(true);
    }

    public void changeInList() {
        changeInList = true;
    }
}
