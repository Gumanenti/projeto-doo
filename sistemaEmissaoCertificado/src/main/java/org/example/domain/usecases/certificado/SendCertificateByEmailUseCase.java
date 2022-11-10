package org.example.domain.usecases.certificado;

import org.example.domain.entities.certificado.Certificado;

import java.util.Optional;


public class SendCertificateByEmailUseCase {
    private final FindCertificadoUseCase findCertificadoUseCase;

    public SendCertificateByEmailUseCase(FindCertificadoUseCase findCertificadoUseCase) {
        this.findCertificadoUseCase = findCertificadoUseCase;
    }

    public void sendMail(String codeCertificado) {

        if (codeCertificado == null || codeCertificado.isEmpty())
            throw new IllegalArgumentException("Código hash não pode ser nulo ou vazio.");

        Optional<Certificado> certificado = findCertificadoUseCase.findOne(codeCertificado);

        if (certificado.isEmpty())
            throw new RuntimeException("Certificado não encontrado.");

        String message = "{to: "+certificado.get().getParticipante().getEmail()+", message: 'Parabéns!!! Agreceçemos o seu empenho, e como prometido aqui está :D', attachment: '"+certificado.get().getCodigo()+".pdf'";
        System.out.println(message);
    }

}