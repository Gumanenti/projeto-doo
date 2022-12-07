package org.example.domain.usecases.certificado;

import org.example.domain.entities.certificado.Certificado;
import org.example.domain.entities.certificado.CertificadoStatus;

import java.util.Optional;

public class CheckCertificadoValidatedHashCodeUseCase {
    private final CertificadoDAO certificadoDAO;

    public CheckCertificadoValidatedHashCodeUseCase(CertificadoDAO certificadoDAO) {
        this.certificadoDAO = certificadoDAO;
    }

    public boolean checkHashCode(String hashCode){

        Optional<Certificado> certificado = certificadoDAO.findOne(hashCode);
        if (certificado.isPresent() && certificado.get().getCertificadoStatus() == CertificadoStatus.VALID) {
            System.out.println("Certificado inválido");
            return true;
        }


        System.out.println("Certificado inválido");
        return false;
    }
}
