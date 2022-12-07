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
import org.example.domain.entities.evento.Evento;

import java.io.IOException;
import java.util.List;

import static org.example.application.main.Main.findCertificadoUseCase;
import static org.example.application.main.Main.findEventoUseCase;

public class MainUI {

    @FXML
    private TableView<Evento> tableViewEvento;
    @FXML
    private TableColumn<Evento, String> cNomeEvento;
    @FXML
    private TableColumn<Evento, String> cCargaHoraria;
    private ObservableList<Evento> tableDataEvento;

    @FXML
    private TableView<Certificado> tableViewCertificado;
    @FXML
    private TableColumn<Certificado, String> CCodigoCertificado;
    @FXML
    private  TableColumn<Certificado, String> CEventoCertificado;
    private ObservableList<Certificado> tableDataCertificado;
    @FXML
    private void initialize(){
        initializeEvento();
        initializeCertificado();
    }
    @FXML
    private void initializeEvento(){
        bindTableViewToItensListEvento();
        bindColumnToValueSourcesEvento();
        loadDataAndShowEvento();
    }

    private void bindTableViewToItensListEvento() {
        tableDataEvento = FXCollections.observableArrayList();
        tableViewEvento.setItems(tableDataEvento);
    }

    private void bindColumnToValueSourcesEvento() {
        cNomeEvento.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cCargaHoraria.setCellValueFactory(new PropertyValueFactory<>("cargaHoraria"));
    }

    private void loadDataAndShowEvento() {
        List<Evento> eventos = findEventoUseCase.findAll();
        tableDataEvento.clear();
        tableDataEvento.addAll(eventos);
    }

    @FXML
    private void initializeCertificado(){
        bindTableViewToItensListCertificado();
        bindColumnToValueSourcesCertificado();
        loadDataAndShowCertificado();
    }

    private void bindTableViewToItensListCertificado() {
        tableDataCertificado = FXCollections.observableArrayList();
        tableViewCertificado.setItems(tableDataCertificado);
    }

    private void bindColumnToValueSourcesCertificado() {
        CCodigoCertificado.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        CEventoCertificado.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getEvento().getNome()));
    }

    private void loadDataAndShowCertificado() {
        List<Certificado> certificados = findCertificadoUseCase.findAll();
        tableDataCertificado.clear();
        tableDataCertificado.addAll(certificados);
    }

    public void showCertificado() throws IOException {
        WindowLoader.setRoot("CertificadoManagementUI");

    }

    public void showEvento() throws IOException {
        WindowLoader.setRoot("EventoManagementUI");

    }

    public void showParticipante() throws IOException {
        WindowLoader.setRoot("ParticipanteManagementUI");

    }

    public void backToLogin() throws IOException {
        WindowLoader.setRoot("LoginAdministradorUI");

    }

    public void generateCerticate() throws IOException {
        WindowLoader.setRoot("PreGerarCertificadosUIManagement");
    }
}
