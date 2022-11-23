package org.example.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.application.view.WindowLoader;
import org.example.domain.entities.certificado.Certificado;
import org.example.domain.entities.certificado.CertificadoStatus;
import org.example.domain.entities.evento.Evento;
import org.example.domain.entities.participante.Participante;

import java.io.IOException;
import java.util.List;

import static org.example.application.main.Main.findCertificadoUseCase;

public class CertificadoManagementUIController {

    @FXML
    private TableView<Certificado> tableView;
    @FXML
    private TableColumn<Certificado, Evento> cEvento;
    @FXML
    private TableColumn<Certificado, Participante> cParticipante;
    @FXML
    private TableColumn<Certificado, String> cCodigo;
    @FXML
    private TableColumn<Certificado, CertificadoStatus> cStatus;

    private ObservableList<Certificado> tableData;

    @FXML
    private void initialize(){
        bindTableViewToItemsList();
        bindColumnToValueSources();
        loadDataAndShow();
    }

    private void bindTableViewToItemsList() {
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);
    }

    private void bindColumnToValueSources() {
        cEvento.setCellValueFactory(new PropertyValueFactory<>("evento"));
        cCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        cParticipante.setCellValueFactory(new PropertyValueFactory<>("participante"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("certificadoStatus"));
    }

    private void loadDataAndShow() {
        List<Certificado> certificados = findCertificadoUseCase.findAll();
        tableData.clear();
        tableData.addAll(certificados);
    }

    private void showCertificadoinMode(UImode mode) throws IOException {
        Certificado selectedItem = tableView.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            WindowLoader.setRoot("CertificadoUI");
            CertificadoUIController controller = (CertificadoUIController) WindowLoader.getController();
            controller.setCertificado(selectedItem, mode);
        }
    }
    public void newCertificado(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("CertificadoUI");
    }

    public void editCertificado(ActionEvent actionEvent) throws IOException {
        showCertificadoinMode(UImode.UPDATE);
    }

    public void detailCertificado(ActionEvent actionEvent) throws IOException {
        showCertificadoinMode(UImode.VIEW);
    }

    public void deleteCertificado(ActionEvent actionEvent) {
        Certificado selectedItem = tableView.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            //não tem delete certificado
        }
    }

    public void backToPrewview(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("MainUI");
    }
}
