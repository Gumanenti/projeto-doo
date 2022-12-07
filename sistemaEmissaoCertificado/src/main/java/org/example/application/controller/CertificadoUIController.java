package org.example.application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.application.view.WindowLoader;
import org.example.domain.entities.certificado.Certificado;
import org.example.domain.entities.certificado.CertificadoStatus;

import java.io.IOException;

import static org.example.application.main.Main.*;


public class CertificadoUIController {
    @FXML
    private TextField txtStatus;
    @FXML
    private TextField txtNomeParticipante;
    @FXML
    private TextField txtNomeEvento;
    @FXML
    private TextField txtHashCode;
    @FXML
    private Button btnInvalid;
    @FXML
    private Button btnReGen;
    @FXML
    private Button btnSendEmail;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnCancel;
    private Certificado certificado;

    @FXML
    private void initialize(){
    }


    public void backToPreviewScene() throws IOException {
        WindowLoader.setRoot("CertificadoManagementUI");
    }

    private void setEntityIntoView(){
        txtHashCode.setText(certificado.getCodigo());
        txtHashCode.setDisable(true);

        txtNomeEvento.setText(certificado.getEvento().getNome());
        txtNomeEvento.setDisable(true);

        txtNomeParticipante.setText(certificado.getParticipante().getNome());
        txtNomeParticipante.setDisable(true);

        txtStatus.setText(certificado.getCertificadoStatus().label);
        txtStatus.setDisable(true);

        if(certificado.getCertificadoStatus() == CertificadoStatus.INVALID){
            btnReGen.setDisable(true);
            btnSendEmail.setDisable(true);
            btnInvalid.setDisable(true);
        }
    }

    public void setCertificado(Certificado selectedItem) {
        if (selectedItem == null)
            throw new IllegalArgumentException("Certificado não pode ser nulo");

        certificado = selectedItem;
        setEntityIntoView();

    }

    public void invalidCertificate() throws IOException {
        if (certificado != null && findCertificadoUseCase.findOne(certificado.getCodigo()).isPresent()) {
            invalidHashCodeCertificadoUseCase.invalidCertificado(certificado.getCodigo());
            setCertificado(findCertificadoUseCase.findOne(certificado.getCodigo()).get());
        }
        WindowLoader.setRoot("CertificadoManagementUI");
    }

    public void reGenCertificate() throws IOException {
        if (certificado != null && findCertificadoUseCase.findOne(certificado.getCodigo()).isPresent()) {
            regenerateCertificadoUseCase.regenerateCertificado(certificado.getCodigo());
        }
        WindowLoader.setRoot("CertificadoManagementUI");
    }

    public void sendToEmail() throws IOException {
        if (certificado != null && findCertificadoUseCase.findOne(certificado.getCodigo()).isPresent()) {
            sendCertificateByEmailUseCase.sendMail(certificado.getCodigo());
        }
        WindowLoader.setRoot("CertificadoManagementUI");
    }
}
