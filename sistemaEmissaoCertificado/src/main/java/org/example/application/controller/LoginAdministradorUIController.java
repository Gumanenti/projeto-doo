package org.example.application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.application.view.WindowLoader;
import org.example.domain.entities.administrador.Administrador;

import java.io.IOException;

import static org.example.application.main.Main.loginAdministradorUseCase;

public class LoginAdministradorUIController {
    @FXML
    private TextField txtLogin;
    @FXML
    private TextField txtSenha;
    @FXML
    private Button btnCadastrar;
    @FXML
    private Button btnLogar;
    private Administrador administrador;

    private void getEntityToView(){
        if(administrador == null){
            administrador = new Administrador();
        }
        administrador.setLogin(txtLogin.getText());
        administrador.setSenha(txtSenha.getText());
    }

    public void loginAdministrador() {
        getEntityToView();
        try{
            loginAdministradorUseCase.autentificarAdministrador(administrador);
            WindowLoader.setRoot("MainUI");
        }catch (Exception e){
            System.err.println(e);
        }
    }

    public void cadastrarAdministrador() throws IOException{
        WindowLoader.setRoot("AdminitradorUI");
    }

}
