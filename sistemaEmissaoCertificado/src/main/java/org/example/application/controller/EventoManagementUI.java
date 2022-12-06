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
import org.example.domain.entities.evento.Evento;

import java.io.IOException;
import java.util.List;

import static org.example.application.main.Main.*;

public class EventoManagementUI {

    @FXML
    private TableView<Evento> tableView;
    @FXML
    private TableColumn<Evento, String> cId;
    @FXML
    private  TableColumn<Evento, String> cNome;
    @FXML
    private TableColumn<Evento, String> cData;
    @FXML
    private TableColumn<Evento, String> cCargaHoraria;
    @FXML
    private TableColumn<Evento, String> cNomePAlestrante;

    private ObservableList<Evento> tableData;

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
        cId.setCellValueFactory(new PropertyValueFactory<>("id"));
        cNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cData.setCellValueFactory(new PropertyValueFactory<>("data"));
        cCargaHoraria.setCellValueFactory(new PropertyValueFactory<>("cargaHoraria"));
        cNomePAlestrante.setCellValueFactory(new PropertyValueFactory<>("nomePalestrante"));
    }

    private void loadDataAndShow() {
        List<Evento> eventos = findEventoUseCase.findAll();
        tableData.clear();
        tableData.addAll(eventos);
    }

    private void showEventoInMode(UIMode mode) throws IOException {
        Evento selectedItem = tableView.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            WindowLoader.setRoot("EventoUI");
            EventoUIController controller = (EventoUIController) WindowLoader.getController();
            controller.setEvento(selectedItem, mode);
        }
    }

    public void newEvento(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("EventoUI");
    }

    public void editEvento(ActionEvent actionEvent) throws IOException {
        showEventoInMode(UIMode.UPDATE);
    }

    public void detailEvento(ActionEvent actionEvent) throws IOException {
        showEventoInMode(UIMode.VIEW);
    }

    public void deleteEvento(ActionEvent actionEvent) {
        Evento selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null){
            removeEventoUseCase.remove(selectedItem);
            loadDataAndShow();
        }
    }

    public void backToPrewview(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("MainUI");
    }
}
