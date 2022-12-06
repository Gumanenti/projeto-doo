package org.example.domain.usecases.certificado;

import org.example.domain.entities.certificado.Certificado;

import java.util.List;

public class CheckCertificadoValidatedHashCodeUseCase {
    private final CertificadoDAO certificadoDAO;

    public CheckCertificadoValidatedHashCodeUseCase(CertificadoDAO certificadoDAO) {
        this.certificadoDAO = certificadoDAO;
    }

    public boolean checkHashCode(String hashCode){
        List<Certificado> certificadoList = certificadoDAO.findAll();

        for(Certificado c : certificadoList)
            if(c.getCodigo().equals(hashCode))
            {
                System.out.println("Certificado válido");
                return true;
            }

        System.out.println("Certificado inválido");
        return false;
    }
}
