package org.example.domain.usecases.certificado;

import org.example.domain.entities.certificado.Certificado;
import org.example.domain.usecases.utils.EntityAlreadyExistsException;
import org.example.domain.usecases.utils.Notification;
import org.example.domain.usecases.utils.Validator;

public class CreateCertificadoUseCase {

    private CertificadoDAO certificadoDAO;

    public CreateCertificadoUseCase(CertificadoDAO certificadoDAO) {
        this.certificadoDAO = certificadoDAO;
    }

    public String insert(Certificado certificado){
        Validator<Certificado> validator = new CertificadoInputRequestValidator();
        Notification notification = validator.validate(certificado);

        if(notification.hasErrors()){
            throw new IllegalArgumentException(notification.errorMessage());
        }

        String codigo = certificado.getCodigo(); //Ctrl + Alt + V
        if(certificadoDAO.findByCodigo(codigo).isPresent()){
            throw new EntityAlreadyExistsException("Esse código já está em uso.");
        }

        return certificadoDAO.create(certificado);
    }
}
