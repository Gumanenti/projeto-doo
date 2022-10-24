package org.example.domain.usecases.certificado;

import org.example.domain.entities.certificado.Certificado;
import org.example.domain.usecases.utils.EntityAlreadyExistsException;
import org.example.domain.usecases.utils.EntityNotFoundException;
import org.example.domain.usecases.utils.Notification;
import org.example.domain.usecases.utils.Validator;

public class UpdateCertificadoUseCase {
    private CertificadoDAO certificadoDAO;

    public UpdateCertificadoUseCase(CertificadoDAO certificadoDAO) {
        this.certificadoDAO = certificadoDAO;
    }

    public boolean update(Certificado certificado){
        Validator<Certificado> validator = new CertificadoInputRequestValidator();
        Notification notification = validator.validate(certificado);

        if(notification.hasErrors()){
            throw new IllegalArgumentException(notification.errorMessage());
        }

        String codigo = certificado.getCodigo(); //Ctrl + Alt + V
        if(certificadoDAO.findOne(codigo).isEmpty()){
            throw new EntityNotFoundException("Certificado não encontrado.");
        }

        return certificadoDAO.update(certificado);
    }
}
