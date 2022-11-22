package org.example.domain.usecases.certificado;

import org.example.domain.entities.certificado.Certificado;

import java.util.UUID;

public class GenerateHashCodeCertificadoUseCase {
    private final CertificadoDAO certificadoDAO;

    public GenerateHashCodeCertificadoUseCase(CertificadoDAO certificadoDAO) {
        this.certificadoDAO = certificadoDAO;
    }

    public void generatorHashcode(Certificado c) {
        UUID hashCode = UUID.randomUUID();

        c.setCodigo(hashCode.toString());
    }
}
