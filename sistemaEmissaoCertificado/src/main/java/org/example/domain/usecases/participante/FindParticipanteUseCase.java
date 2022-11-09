package org.example.domain.usecases.participante;

import org.example.domain.entities.participante.Participante;

import java.util.List;
import java.util.Optional;

public class FindParticipanteUseCase {
    private ParticipanteDAO participanteDAO;

    public FindParticipanteUseCase(ParticipanteDAO participanteDAO) {
        this.participanteDAO = participanteDAO;
    }

    public Optional<Participante> findOne(String cpf){
        if (cpf == null) {
            throw new IllegalArgumentException("CPF não pode ser nulo.");
        }
        return participanteDAO.findOne(cpf);
    }

    public List<Participante> findAll(){
        return participanteDAO.findAll();
    }

}
