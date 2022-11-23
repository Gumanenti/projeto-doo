package org.example.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.domain.entities.certificado.CertificadoStatus;

public class CertificadoStatusManegementUI {

    @FXML
    private TableView<CertificadoStatus> tableView;
    @FXML
    private TableColumn<CertificadoStatus, Boolean> cValidade;

    private ObservableList<CertificadoStatus> tableData;

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
        cValidade.setCellValueFactory(new PropertyValueFactory<>("validade"));
    }

    private void loadDataAndShow() {
    }

    public void newCertificadoStatus(ActionEvent actionEvent) {
    }

    public void editCertificadoStatus(ActionEvent actionEvent) {
    }

    public void detailCertificadoStatus(ActionEvent actionEvent) {
    }

    public void deleteCertificadoStatus(ActionEvent actionEvent) {
    }

    public void backToPrewview(ActionEvent actionEvent) {
    }
}
