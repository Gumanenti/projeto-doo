package org.example.domain.usecases.evento;

import org.example.domain.entities.evento.Evento;
import org.example.domain.entities.participante.Participante;

import java.util.List;
import java.util.Optional;

public class FindEventoUseCase {
    private EventoDAO eventoDAO;

    public FindEventoUseCase(EventoDAO eventoDAO) {
        this.eventoDAO = eventoDAO;
    }

    public Optional<Evento> findOne(Integer id){
        if (id == null) {
            throw new IllegalArgumentException("Não pode ser nulo.");
        }
        return eventoDAO.findOne(id);
    }

    public List<Evento> findAll(){
        return eventoDAO.findAll();
    }

}
