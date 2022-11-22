package org.example.domain.usecases.certificado;

import org.example.domain.entities.certificado.Certificado;
import org.example.domain.entities.certificado.CertificadoStatus;

import java.io.File;
import java.util.Optional;

public class RegenerateCertificadoUseCase {
    private final CertificadoDAO certificadoDAO;
    private final GenerateCertificadoUseCase generateCertificadoUseCase;
    private final String pathRelatorio;

    public RegenerateCertificadoUseCase(CertificadoDAO certificadoDAO, GenerateCertificadoUseCase generateCertificadoUseCase, String pathRelatorio) {
        this.certificadoDAO = certificadoDAO;
        this.generateCertificadoUseCase = generateCertificadoUseCase;
        this.pathRelatorio = pathRelatorio;
    }

    public void regenerateCertificado(String hashCode){
        if (hashCode == null || hashCode.isEmpty())
            throw new RuntimeException("Código hash não pode ser vazio ou nulo!");

        Optional<Certificado> certificado;

        certificado = certificadoDAO.findByCodigo(hashCode);
        if (certificado.isEmpty())
            throw new RuntimeException("Certificado não encontrado");
        certificado.get().setCertificadoStatus(new CertificadoStatus(false));
        certificadoDAO.update(certificado.get());
        deletePdfFile(hashCode);

        generateCertificadoUseCase.createCertificado(certificado.get().getEvento().getId(), certificado.get().getParticipante().getCpf());
    }

    private void deletePdfFile(String hascode){
        File file = new File(this.pathRelatorio+ hascode + ".pdf");
        if (file.delete()){
            System.out.println("Arquivo apagado!");
        }
        else{
            System.out.println("Arquivo não foi apagado!");
        }
    }

}
