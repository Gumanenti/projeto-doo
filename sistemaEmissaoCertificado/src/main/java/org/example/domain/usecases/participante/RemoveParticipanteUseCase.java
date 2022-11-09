package org.example.domain.usecases.participante;

import org.example.domain.entities.participante.Participante;
import org.example.domain.usecases.utils.EntityNotFoundException;

public class RemoveParticipanteUseCase {

    private ParticipanteDAO participanteDAO;

    public RemoveParticipanteUseCase(ParticipanteDAO participanteDAO) {
        this.participanteDAO = participanteDAO;
    }

    public boolean remove(String cpf){
        if(cpf == null || participanteDAO.findOne(cpf).isEmpty())
            throw new EntityNotFoundException("Participante não encontrado");
        return participanteDAO.deleteByKey(cpf);
    }

    public boolean remove(Participante participante){
        if(participante == null || participanteDAO.findOne(participante.getCpf()).isEmpty())
            throw new EntityNotFoundException("Participante não encontrado");
        return participanteDAO.delete(participante);
    }
}
