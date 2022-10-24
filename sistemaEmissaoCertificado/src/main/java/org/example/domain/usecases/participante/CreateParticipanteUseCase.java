package org.example.domain.usecases.participante;

import org.example.domain.entities.certificado.Certificado;
import org.example.domain.entities.participante.Participante;
import org.example.domain.usecases.utils.EntityAlreadyExistsException;
import org.example.domain.usecases.utils.Notification;
import org.example.domain.usecases.utils.Validator;

public class CreateParticipanteUseCase {

    private ParticipanteDAO participanteDAO;

    public CreateParticipanteUseCase(ParticipanteDAO participanteDAO) {
        this.participanteDAO = participanteDAO;
    }

    public String insert(Participante participante){
        Validator<Participante> validator = new ParticipanteInputRequestValidator();
        Notification notification = validator.validate(participante);

        if(notification.hasErrors()){
            throw new IllegalArgumentException(notification.errorMessage());
        }

        String cpf = participante.getCpf(); //Ctrl + Alt + V
        if(participanteDAO.findByCPF(cpf).isPresent()){
            throw new EntityAlreadyExistsException("Esse CPF já está em uso.");
        }

        return participanteDAO.create(participante);
    }
}
