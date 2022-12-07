package org.example.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.application.view.WindowLoader;
import org.example.domain.entities.administrador.Administrador;

import java.io.IOException;

import static org.example.application.main.Main.*;


public class AdministradorUIController {


    @FXML
    private TextField txtLogin;
    @FXML
    private TextField txtSenha;
    @FXML
    private TextField txtPalavraChave;
    @FXML
    private Button btnConfirm;
    @FXML
    private Button btnCancel;

    private Administrador administrador;

    private void getEntityToView(){
        if(administrador == null){
            administrador = new Administrador();
        }
        administrador.setLogin(txtLogin.getText());
        administrador.setSenha(txtSenha.getText());
        administrador.setPalavraChave(txtPalavraChave.getText());
    }

    private void setEntityIntoView(){
        txtLogin.setText(administrador.getLogin());
        txtLogin.setDisable(true);
        txtSenha.setText(administrador.getSenha());
        txtPalavraChave.setText(administrador.getPalavraChave());
    }

    public void saveOrUpdate(ActionEvent actionEvent) throws IOException {
        getEntityToView();
        if (findAdministradorUseCase.findOne(administrador.getLogin()).isEmpty()){
            createAdministradorUseCase.insert(administrador);
        } else {
            updateAdministradorUseCase.update(administrador);
        }
        WindowLoader.setRoot("LoginAdministradorUI");
    }

    public void backToPreviewScenne(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("LoginAdministradorUI");
    }

    public void setAdministrador(Administrador administrador, UIMode mode) {
        if (administrador == null)
            throw new IllegalArgumentException("Administrador não pode ser nulo");

        this.administrador = administrador;
        setEntityIntoView();

        if (mode == UIMode.VIEW)
            configureViewMode();

    }

    private void configureViewMode() {
        btnCancel.setLayoutX(btnConfirm.getLayoutX());
        btnCancel.setLayoutX(btnConfirm.getLayoutY());
        btnCancel.setText("Fechar");

        btnConfirm.setVisible(false);
        txtLogin.setDisable(true);
        txtSenha.setDisable(true);
        txtPalavraChave.setDisable(true);
    }

}
