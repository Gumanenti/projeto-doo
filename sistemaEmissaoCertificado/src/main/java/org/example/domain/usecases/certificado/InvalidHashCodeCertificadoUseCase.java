package org.example.domain.usecases.certificado;

import org.example.domain.entities.certificado.Certificado;
import org.example.domain.entities.certificado.CertificadoStatus;

import java.util.Optional;

public class InvalidHashCodeCertificadoUseCase {
    private final CertificadoDAO certificadoDAO;

    public InvalidHashCodeCertificadoUseCase(CertificadoDAO certificadoDAO) {
        this.certificadoDAO = certificadoDAO;
    }

    public void invalidCertificado(String hashCode){
        if (hashCode == null || hashCode.isEmpty())
            throw new RuntimeException("Código hash não pode ser vazio ou nulo!");

        Optional<Certificado> certificado;

        certificado = certificadoDAO.findByCodigo(hashCode);
        if (certificado.isEmpty())
            throw new RuntimeException("Certificado não encontrado");

        certificado.get().setCertificadoStatus(new CertificadoStatus(false));
        certificadoDAO.delete(certificado.get());

    }
}
