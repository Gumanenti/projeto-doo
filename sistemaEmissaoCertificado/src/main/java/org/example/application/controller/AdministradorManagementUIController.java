package org.example.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.application.view.WindowLoader;
import org.example.domain.entities.administrador.Administrador;

import java.io.IOException;
import java.util.List;

import static org.example.application.main.Main.findAdministradorUseCase;
import static org.example.application.main.Main.removeAdministradorUseCase;

public class AdministradorManagementUIController {
    
    @FXML
    private TableView<Administrador> tableView;
    @FXML
    private TableColumn<Administrador, String> cLogin;
    @FXML
    private  TableColumn<Administrador, String> cSenha;
    @FXML
    private TableColumn<Administrador, String> cPalavraChave;

    private ObservableList<Administrador> tableData;

    @FXML
    private void inicitialize(){
        bindTableViewToItensList();
        bindColumnToValueSources();
        loadDataAndShow();
    }

    private void bindTableViewToItensList() {
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);
    }

    private void bindColumnToValueSources() {
        cLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        cSenha.setCellValueFactory(new PropertyValueFactory<>("senha"));
        cPalavraChave.setCellValueFactory(new PropertyValueFactory<>("palavraChave"));
    }

    private void loadDataAndShow() {
        List<Administrador> administradors = findAdministradorUseCase.findAll();
        tableData.clear();
        tableData.addAll(administradors);
    }

    private void showAdministradorInMode(UImode mode) throws IOException {
        Administrador selectedItem = tableView.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            WindowLoader.setRoot("AdminitradorUI");
            AdministradorUIController controller = (AdministradorUIController) WindowLoader.getController();
            controller.setAdministrador(selectedItem, mode);
        }
    }
    public void newAdministrador(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("AdminitradorUI");
    }

    public void editAdministrador(ActionEvent actionEvent) throws IOException {
        showAdministradorInMode(UImode.UPDATE);
    }

    public void datialAdministrador(ActionEvent actionEvent) throws IOException {
        showAdministradorInMode(UImode.VIEW);
    }

    public void deleteAdministrador(ActionEvent actionEvent) {
        Administrador selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null){
            removeAdministradorUseCase.remove(selectedItem);
            loadDataAndShow();
        }
    }

    public void backToPrewview(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("MainUI");
    }
}
