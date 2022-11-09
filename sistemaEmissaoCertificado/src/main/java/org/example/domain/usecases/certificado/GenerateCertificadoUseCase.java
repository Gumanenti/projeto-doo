package org.example.domain.usecases.certificado;

import org.example.domain.entities.certificado.Certificado;
import org.example.domain.entities.certificado.CertificadoStatus;
import org.example.domain.usecases.evento.FindEventoUseCase;
import org.example.domain.usecases.participante.FindParticipanteUseCase;
import org.example.domain.usecases.utils.EntityAlreadyExistsException;
import org.example.domain.usecases.utils.Notification;
import org.example.domain.usecases.utils.Validator;

public class GenerateCertificadoUseCase {

    private CertificadoDAO certificadoDAO;
    private FindParticipanteUseCase findParticipanteUseCase;
    private FindEventoUseCase findEventoUseCase;
    private GeneratePDFCertificadoUseCase generatePDFCertificadoUseCase;
    private GenerateHashCodeCertificadoUseCase generateHashCodeCertificadoUseCase;

    public GenerateCertificadoUseCase(CertificadoDAO certificadoDAO, FindParticipanteUseCase findParticipanteUseCase, FindEventoUseCase findEventoUseCase, GeneratePDFCertificadoUseCase generatePDFCertificadoUseCase, GenerateHashCodeCertificadoUseCase generateHashCodeCertificadoUseCase) {
        this.certificadoDAO = certificadoDAO;
        this.findParticipanteUseCase = findParticipanteUseCase;
        this.findEventoUseCase = findEventoUseCase;
        this.generatePDFCertificadoUseCase = generatePDFCertificadoUseCase;
        this.generateHashCodeCertificadoUseCase = generateHashCodeCertificadoUseCase;

    }
    public String createCertificado(Integer eventoId, String participantCpf){
        Certificado certificado = new Certificado();

        if (eventoId == null || (participantCpf == null || participantCpf.isEmpty()))
            throw new IllegalArgumentException("Evento ID não e/ou Participante ID é/são vazios ou nulos");

        certificado.setParticipante(findParticipanteUseCase.findOne(participantCpf).get());
        certificado.setEvento(findEventoUseCase.findOne(eventoId).get());

        try {
            generateHashCodeCertificadoUseCase.generatorHashcode(certificado);
            Validator<Certificado> validator = new CertificadoInputRequestValidator();
            Notification notification = validator.validate(certificado);

            if (notification.hasErrors())
                throw new IllegalArgumentException(notification.errorMessage());

            certificado.setCertificadoStatus(new CertificadoStatus(true));

            insert(certificado);
            return certificado.getCodigo();

        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return null;
    }

    private String insert(Certificado certificado){
        Validator<Certificado> validator = new CertificadoInputRequestValidator();
        Notification notification = validator.validate(certificado);

        if(notification.hasErrors()){
            throw new IllegalArgumentException(notification.errorMessage());
        }

        String codigo = certificado.getCodigo(); //Ctrl + Alt + V
        if(certificadoDAO.findByCodigo(codigo).isPresent()){
            throw new EntityAlreadyExistsException("Esse código já está em uso.");
        }

        return certificadoDAO.create(certificado);
    }
}
