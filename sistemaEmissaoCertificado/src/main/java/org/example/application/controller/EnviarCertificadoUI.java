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

import static org.example.application.main.Main.certificateListToSendEmail;
import static org.example.application.main.Main.sendCertificateByEmailUseCase;

public class EnviarCertificadoUI {

    @FXML
    private TableView<Certificado> tableView;
    @FXML
    private TableColumn<Certificado, String> cCodigo;
    @FXML
    private  TableColumn<Certificado, String> cEvento;
    @FXML
    private TableColumn<Certificado, String> cParticipante;
    private ObservableList<Certificado> tableData;
    @FXML
    private void initialize(){
        bindTableViewToItemsList();
        bindColumnsToValueSources();
        loadDataAndShow();
    }

    private void bindTableViewToItemsList() {
        tableData = FXCollections.observableArrayList();
        tableView.setItems(tableData);
    }

    private void bindColumnsToValueSources() {
        cCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        cEvento.setCellValueFactory(new PropertyValueFactory<>("evento"));
        cEvento.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getEvento().getNome()));
        cParticipante.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getParticipante().getNome()));
    }

    private void loadDataAndShow() {
        List<Certificado> certificados = certificateListToSendEmail;
        tableData.clear();
        tableData.addAll(certificados);
    }

    public void backToPrewview() throws IOException {
        WindowLoader.setRoot("MainUI");
    }

    public void removeCertificado() {
        Certificado selectedItem = tableView.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            certificateListToSendEmail.remove(selectedItem);
        }
        loadDataAndShow();
    }

    public void sendOneCertificado() {
        Certificado certificado = tableView.getSelectionModel().getSelectedItem();
        if (certificado != null){
            sendCertificateByEmailUseCase.sendMail(certificado.getCodigo());
            certificateListToSendEmail.remove(certificado);
            loadDataAndShow();
        }
    }

    public void sendAllCertificado() throws IOException {
        for (Certificado c : certificateListToSendEmail){
            sendCertificateByEmailUseCase.sendMail(c.getCodigo());
        }
        certificateListToSendEmail.clear();
        WindowLoader.setRoot("MainUI");
    }
}
