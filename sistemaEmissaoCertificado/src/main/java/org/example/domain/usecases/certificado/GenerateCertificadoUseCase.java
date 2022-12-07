package org.example.domain.usecases.certificado;

import org.example.domain.entities.certificado.Certificado;
import org.example.domain.entities.certificado.CertificadoStatus;
import org.example.domain.usecases.participante.FindParticipanteUseCase;
import org.example.domain.usecases.utils.EntityAlreadyExistsException;
import org.example.domain.usecases.utils.Notification;
import org.example.domain.usecases.utils.Validator;
import org.example.domain.usecases.evento.FindEventoUseCase;

public class GenerateCertificadoUseCase {

    private final CertificadoDAO certificadoDAO;
    private final FindParticipanteUseCase findParticipanteUseCase;
    private final FindEventoUseCase findEventoUseCase;
    private final GeneratePDFCertificadoUseCase generatePDFCertificadoUseCase;
    private final GenerateHashCodeCertificadoUseCase generateHashCodeCertificadoUseCase;
    private final SendCertificateByEmailUseCase sendCertificateByEmailUseCase;

    public GenerateCertificadoUseCase(CertificadoDAO certificadoDAO, FindParticipanteUseCase findParticipanteUseCase, FindEventoUseCase findEventoUseCase, GeneratePDFCertificadoUseCase generatePDFCertificadoUseCase, GenerateHashCodeCertificadoUseCase generateHashCodeCertificadoUseCase, SendCertificateByEmailUseCase sendCertificateByEmailUseCase) {
        this.certificadoDAO = certificadoDAO;
        this.findParticipanteUseCase = findParticipanteUseCase;
        this.findEventoUseCase = findEventoUseCase;
        this.generatePDFCertificadoUseCase = generatePDFCertificadoUseCase;
        this.generateHashCodeCertificadoUseCase = generateHashCodeCertificadoUseCase;
        this.sendCertificateByEmailUseCase = sendCertificateByEmailUseCase;

    }
    public void createCertificado(Integer eventoId, String participantCpf){
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
            generatePDFCertificadoUseCase.generatePDF(certificado.getCodigo());
            sendCertificateByEmailUseCase.sendMail(certificado.getCodigo());

        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    private void insert(Certificado certificado){
        Validator<Certificado> validator = new CertificadoInputRequestValidator();
        Notification notification = validator.validate(certificado);

        if(notification.hasErrors()){
            throw new IllegalArgumentException(notification.errorMessage());
        }

        String codigo = certificado.getCodigo(); //Ctrl + Alt + V
        if(certificadoDAO.findOne(codigo).isPresent()){
            throw new EntityAlreadyExistsException("Esse código já está em uso.");
        }

        certificadoDAO.create(certificado);
    }
}
