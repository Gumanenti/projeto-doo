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
import org.example.domain.entities.certificado.Certificado;
import org.example.domain.entities.participante.Participante;

import java.io.IOException;
import java.util.List;

import static org.example.application.main.Main.*;

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
        cParticipante.setCellValueFactory(new PropertyValueFactory<>("participante"));
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
            certificateListToSendEmail.remove(c);
        }
        WindowLoader.setRoot("MainUI");
    }
}
