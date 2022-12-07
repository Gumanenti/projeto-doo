package org.example.application.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        loadDataAndShow();
    }

    private void loadDataAndShow() {
        List<Certificado> certificados = findCertificadoUseCase.findAll();
        tableData.clear();
        tableData.addAll(certificados);
    }

    private void bindColumnToValueSources() {
        cEvento.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getEvento().getNome()));
        cParticipante.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getParticipante().getNome()));
        cCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("certificadoStatus"));
        cStatus.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getCertificadoStatus().label));
    }

    private void bindTableViewToItensList() {
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);
    }

    private void showCertificadoInMode() throws IOException {
        Certificado selectedItem = tableView.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            WindowLoader.setRoot("CertificadoUI");
            CertificadoUIController controller = (CertificadoUIController) WindowLoader.getController();
            controller.setCertificado(selectedItem);
        }
    }

    public void detailCertificado() throws IOException {
        showCertificadoInMode();
    }

    public void invalidCertificado() {
        Certificado selectedItem = tableView.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            invalidHashCodeCertificadoUseCase.invalidCertificado(selectedItem.getCodigo());
        }
        loadDataAndShow();
    }

    public void backToPrewview() throws IOException {
        WindowLoader.setRoot("MainUI");
    }
}
