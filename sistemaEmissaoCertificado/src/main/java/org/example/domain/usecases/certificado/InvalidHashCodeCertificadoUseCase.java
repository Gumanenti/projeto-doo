package org.example.domain.usecases.certificado;

import org.example.domain.entities.certificado.Certificado;
import org.example.domain.entities.certificado.CertificadoStatus;

import java.io.File;
import java.util.Optional;

public class InvalidHashCodeCertificadoUseCase {
    private final CertificadoDAO certificadoDAO;
    private final String pathRelatorio;

    public InvalidHashCodeCertificadoUseCase(CertificadoDAO certificadoDAO, String pathRelatorio) {
        this.certificadoDAO = certificadoDAO;
        this.pathRelatorio = pathRelatorio;
    }

    public void invalidCertificado(String hashCode){
        if (hashCode == null || hashCode.isEmpty())
            throw new RuntimeException("Código hash não pode ser vazio ou nulo!");

        Optional<Certificado> certificado;

        certificado = certificadoDAO.findOne(hashCode);
        if (certificado.isEmpty())
            throw new RuntimeException("Certificado não encontrado");

        certificado.get().setCertificadoStatus(new CertificadoStatus(false));
        certificadoDAO.update(certificado.get());
        deletePdfFile(hashCode);
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
