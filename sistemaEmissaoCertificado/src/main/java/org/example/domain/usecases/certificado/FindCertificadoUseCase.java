package org.example.domain.usecases.certificado;

import org.example.domain.entities.certificado.Certificado;

import java.util.List;
import java.util.Optional;

public class FindCertificadoUseCase {
    private CertificadoDAO certificadoDAO;

    public FindCertificadoUseCase(CertificadoDAO certificadoDAO) {
        this.certificadoDAO = certificadoDAO;
    }

    public Optional<Certificado> findOne(String codigo){
        if (codigo == null) {
            throw new IllegalArgumentException("Não pode ser nulo.");
        }
        return certificadoDAO.findOne(codigo);
    }

    public List<Certificado> findAll(){
        return certificadoDAO.findAll();
    }

}
