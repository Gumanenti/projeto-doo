package org.example.application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import org.example.application.view.WindowLoader;
import org.example.domain.entities.certificado.Certificado;
import org.example.domain.entities.certificado.CertificadoStatus;
import org.example.domain.entities.evento.Evento;
import org.example.domain.entities.participante.Participante;

import java.io.IOException;

import static org.example.application.main.Main.findEventoUseCase;
import static org.example.application.main.Main.updateCertificadoUseCase;


public class CertificadoUIController {
    public ComboBox cbEvento;
    @FXML
    private TextField txtEvento;
    @FXML
    private TextField txtParticipante;
    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtCertificadoStatus;
    @FXML
    private Button btnConfirm;
    @FXML
    private Button btnCancel;

    private Certificado certificado;
    private ObservableList<Evento> listViewEvent;

    @FXML
    private void initialize(){
        loadComboEvent();
    }

    private void getEntityToView(){
        if(certificado == null){
            certificado = new Certificado();
        }
        certificado.setEvento((Evento) txtEvento.getUserData());
        certificado.setParticipante((Participante) txtParticipante.getUserData());
        certificado.setCodigo(txtCodigo.getText());
        certificado.setCertificadoStatus((CertificadoStatus) txtCertificadoStatus.getUserData());
    }

    private void setEntityIntoView(){
        txtEvento.setText(String.valueOf(certificado.getEvento()));
        txtParticipante.setText(String.valueOf(certificado.getParticipante()));
        txtCodigo.setText(certificado.getCodigo());
        txtCertificadoStatus.setText(String.valueOf(certificado.getCertificadoStatus()));
    }
    public void backToPreviewScene(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("CertificadoManagementUI");
    }

    public void saveOrUpdate(ActionEvent actionEvent) throws IOException {
        getEntityToView();
        if (certificado.getCodigo() == null){
            //generateCertificadoUseCase.insert(certificado);
        } else {
            updateCertificadoUseCase.update(certificado);
        }
        WindowLoader.setRoot("CertificadoManagementUI");
    }

    public void setCertificado(Certificado selectedItem, UIMode mode) {
        if (certificado == null){
            throw new IllegalArgumentException("Certificado não pode ser nulo");
        } else {
            this.certificado = certificado;
            setEntityIntoView();
        }
        if (mode == UIMode.VIEW){
            configureViewMode();
        }
    }

    private void configureViewMode() {
        btnCancel.setLayoutX(btnConfirm.getLayoutX());
        btnCancel.setLayoutX(btnConfirm.getLayoutY());
        btnCancel.setText("Fechar");

        btnConfirm.setVisible(false);
        txtEvento.setDisable(true);
        txtParticipante.setDisable(true);
        txtCodigo.setDisable(true);
        txtCertificadoStatus.setDisable(true);
    }

    private void loadComboEvent(){
        listViewEvent = FXCollections.observableArrayList(findEventoUseCase.findAll());
        cbEvento.setItems(listViewEvent);
        cbEvento.setConverter(new StringConverter<Evento>() {
            @Override
            public String toString(Evento object) {
                if (object != null){
                    return object.getNome();
                }
                return null;
            }

            @Override
            public Evento fromString(String string) {
                return null;
            }
        });
    }

}
