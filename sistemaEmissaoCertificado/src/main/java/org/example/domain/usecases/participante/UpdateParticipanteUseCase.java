package org.example.domain.usecases.participante;

import org.example.domain.entities.participante.Participante;
import org.example.domain.usecases.utils.EntityNotFoundException;
import org.example.domain.usecases.utils.Notification;
import org.example.domain.usecases.utils.Validator;

public class UpdateParticipanteUseCase {
    private ParticipanteDAO participanteDAO;

    public UpdateParticipanteUseCase(ParticipanteDAO participanteDAO) {
        this.participanteDAO = participanteDAO;
    }

    public boolean update(Participante participante){
        Validator<Participante> validator = new ParticipanteInputRequestValidator();
        Notification notification = validator.validate(participante);

        if(notification.hasErrors()){
            throw new IllegalArgumentException(notification.errorMessage());
        }

        String codigo = participante.getCpf();
        if(participanteDAO.findOne(codigo).isEmpty()){
            throw new EntityNotFoundException("Participante não encontrado.");
        }

        return participanteDAO.update(participante);
    }
}
