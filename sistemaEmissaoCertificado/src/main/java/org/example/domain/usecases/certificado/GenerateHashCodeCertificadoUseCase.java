package org.example.domain.usecases.certificado;

import org.example.domain.entities.certificado.Certificado;
import org.example.domain.usecases.utils.GenerateHashCode;

public class GenerateHashCodeCertificadoUseCase {
    private final CertificadoDAO certificadoDAO;

    public GenerateHashCodeCertificadoUseCase(CertificadoDAO certificadoDAO) {
        this.certificadoDAO = certificadoDAO;
    }

    public void generatorHashcode(Certificado c) throws Exception {
        GenerateHashCode generateHashCode = new GenerateHashCode();
        String toHashCode = c.getCodigo() + c.getParticipante().getCpf() + c.getEvento().getId();

        String hashCode = generateHashCode.genHashCode(toHashCode);
        c.setCodigo(hashCode.toString());
        certificadoDAO.update(c);
    }
}
