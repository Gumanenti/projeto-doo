package org.example.domain.usecases.certificado;

import org.example.domain.entities.certificado.Certificado;
import org.example.domain.entities.certificado.CertificadoStatus;
import org.example.domain.entities.evento.Evento;
import org.example.domain.entities.participante.Participante;
import org.example.domain.usecases.evento.FindEventoUseCase;
import org.example.domain.usecases.participante.FindParticipanteUseCase;
import org.example.domain.usecases.utils.EntityAlreadyExistsException;
import org.example.domain.usecases.utils.EntityNotFoundException;
import org.example.domain.usecases.utils.Notification;
import org.example.domain.usecases.utils.Validator;

import java.util.Optional;

public class GenerateCertificadoUseCase {

    private final CertificadoDAO certificadoDAO;
    private final FindParticipanteUseCase findParticipanteUseCase;
    private final FindEventoUseCase findEventoUseCase;
    private final GeneratePDFCertificadoUseCase generatePDFCertificadoUseCase;
    private final GenerateHashCodeCertificadoUseCase generateHashCodeCertificadoUseCase;

    public GenerateCertificadoUseCase(CertificadoDAO certificadoDAO, FindParticipanteUseCase findParticipanteUseCase, FindEventoUseCase findEventoUseCase, GeneratePDFCertificadoUseCase generatePDFCertificadoUseCase, GenerateHashCodeCertificadoUseCase generateHashCodeCertificadoUseCase) {
        this.certificadoDAO = certificadoDAO;
        this.findParticipanteUseCase = findParticipanteUseCase;
        this.findEventoUseCase = findEventoUseCase;
        this.generatePDFCertificadoUseCase = generatePDFCertificadoUseCase;
        this.generateHashCodeCertificadoUseCase = generateHashCodeCertificadoUseCase;

    }
    public String createCertificado(Integer eventoId, String participantCpf){
        Certificado certificado = new Certificado();
        Optional<Participante> participante;
        Optional<Evento> evento;


        if (eventoId == null || (participantCpf == null || participantCpf.isEmpty()))
            throw new IllegalArgumentException("Evento ID não e/ou Participante ID é/são vazios ou nulos");

        participante = findParticipanteUseCase.findOne(participantCpf);

        if (participante.isEmpty())
            throw new EntityNotFoundException("Participante não foi encontrado!");

        evento = findEventoUseCase.findOne(eventoId);

        if (evento.isEmpty())
            throw new EntityNotFoundException("Evento não foi encontrado!");

        certificado.setEvento(evento.get());
        certificado.setParticipante(participante.get());

        generateHashCodeCertificadoUseCase.generatorHashcode(certificado);

        Validator<Certificado> validator = new CertificadoInputRequestValidator();
        Notification notification = validator.validate(certificado);
        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());

        certificado.setCertificadoStatus(new CertificadoStatus(true));

        String codigo = certificado.getCodigo(); //Ctrl + Alt + V
        if(certificadoDAO.findByCodigo(codigo).isPresent()){
            throw new EntityAlreadyExistsException("Esse código já está em uso.");
        }

        certificadoDAO.create(certificado);

        generatePDFCertificadoUseCase.generatePDF(certificado.getCodigo());

        return certificado.getCodigo();
    }

}
