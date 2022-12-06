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

import java.io.IOException;
import java.util.List;

import static org.example.application.main.Main.findCertificadoUseCase;
import static org.example.application.main.Main.invalidHashCodeCertificadoUseCase;

public class CertificadoManagementUIController {

    @FXML
    private TableView<Certificado> tableView;
    @FXML
    private TableColumn<Certificado, String> cEvento;
    @FXML
    private  TableColumn<Certificado, String> cParticipante;
    @FXML
    private TableColumn<Certificado, String> cCodigo;
    @FXML
    private TableColumn<Certificado, String> cStatus;

    private ObservableList<Certificado> tableData;

    @FXML
    private void initialize(){
        bindTableViewToItensList();
        bindColumnToValueSources();
        //loadDataAndShow();
    }

    private void loadDataAndShow() {
        List<Certificado> certificados = findCertificadoUseCase.findAll();
        tableData.clear();
        tableData.addAll(certificados);
    }

    private void bindColumnToValueSources() {
        cEvento.setCellValueFactory(new PropertyValueFactory<>("evento"));
        cParticipante.setCellValueFactory(new PropertyValueFactory<>("participante"));
        cCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("certificadoStatus"));

    }

    private void bindTableViewToItensList() {
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);
    }

    private void showCertificadoInMode(UIMode mode) throws IOException {
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
        showCertificadoInMode(UIMode.UPDATE);
    }

    public void detailCertificado(ActionEvent actionEvent) throws IOException {
        showCertificadoInMode(UIMode.VIEW);
    }

    public void deleteCertificado(ActionEvent actionEvent) {

    }

    public void backToPrewview(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("MainUI");
    }
}
