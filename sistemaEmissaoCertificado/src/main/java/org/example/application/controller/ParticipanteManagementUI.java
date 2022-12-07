package org.example.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.application.view.WindowLoader;
import org.example.domain.entities.participante.Participante;

import java.io.IOException;
import java.util.List;

import static org.example.application.main.Main.findParticipanteUseCase;
import static org.example.application.main.Main.removeParticipanteUseCase;


public class ParticipanteManagementUI {
    @FXML
    private TableView<Participante> tableView;
    @FXML
    private TableColumn<Participante, String> cNome;
    @FXML
    private  TableColumn<Participante, String> cEmail;
    @FXML
    private TableColumn<Participante, String> cCPF;

    private ObservableList<Participante> tableData;

    @FXML
    private void initialize(){
        bindTableViewToItensList();
        bindColumnToValueSources();
        loadDataAndShow();
    }

    private void bindTableViewToItensList() {
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);
    }

    private void bindColumnToValueSources() {
        cNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        cCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
    }

    private void loadDataAndShow() {
        List<Participante> participantes = findParticipanteUseCase.findAll();
        tableData.clear();
        tableData.addAll(participantes);
    }

    private void showParticipanteInMode(UIMode mode) throws IOException {
        Participante selectedItem = tableView.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            WindowLoader.setRoot("ParticipanteUI");
            ParticipanteUIController controller = (ParticipanteUIController) WindowLoader.getController();
            controller.setParticipante(selectedItem, mode);
        }
    }
    public void newParticipante() throws IOException {
        WindowLoader.setRoot("ParticipanteUI");
    }

    public void editParticipante() throws IOException {
        showParticipanteInMode(UIMode.UPDATE);
    }

    public void detailParticipante() throws IOException {
        showParticipanteInMode(UIMode.VIEW);
    }

    public void deleteParticipante() {
        Participante selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null){
            removeParticipanteUseCase.remove(selectedItem);
            loadDataAndShow();
        }
    }

    public void backToPreviewScenne() throws IOException {
        WindowLoader.setRoot("MainUI");
    }
}
