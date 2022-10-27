package org.example.domain.usecases.certificado;

import org.example.domain.entities.certificado.Certificado;
import org.example.domain.usecases.utils.Notification;
import org.example.domain.usecases.utils.Validator;

public class CertificadoInputRequestValidator extends Validator<Certificado> {
    @Override
    public Notification validate(Certificado certificado) {
        Notification notification = new Notification();

        if(certificado == null){
            notification.addError("Certificado não existe");
            return notification;
        }
        if(nullOrEmpty(certificado.getCodigo())){
            notification.addError("Código não existe ou está vazio");
        }
        //ERRO + terminar os outros atributos

        if(nullOrEmpty(String.valueOf(certificado.getCertificadoStatus()))){
            notification.addError("Código não existe ou está vazio");
        }

        return notification;
    }
}
