package org.example.domain.usecases.certificado;

import org.example.domain.entities.certificado.Certificado;
import org.example.domain.usecases.utils.EntityNotFoundException;

public class RemoveCertificadoUseCase {

    private CertificadoDAO certificadoDAO;

    public RemoveCertificadoUseCase(CertificadoDAO certificadoDAO) {
        this.certificadoDAO = certificadoDAO;
    }

    public boolean remove(String codigo){
        if(codigo == null || certificadoDAO.findOne(codigo).isEmpty())
            throw new EntityNotFoundException("Certificado não encontrado");
        return certificadoDAO.deleteByKey(codigo);
    }

    public boolean remove(Certificado certificado){
        if(certificado == null || certificadoDAO.findOne(certificado.getCodigo()).isEmpty())
            throw new EntityNotFoundException("Certificado não encontrado");
        return certificadoDAO.delete(certificado);
    }
}
