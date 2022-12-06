package org.example.domain.usecases.evento;

import org.example.domain.entities.evento.Evento;
import org.example.domain.usecases.utils.EntityNotFoundException;

public class RemoveEventoUseCase {
    private final EventoDAO eventoDAO;

    public RemoveEventoUseCase(EventoDAO eventoDAO) {
        this.eventoDAO = eventoDAO;
    }

    public boolean remove(Evento evento){
        if (evento == null)
            throw new NullPointerException("Evento não pode ser nulo");
        return remove(evento.getId());
    }

    public boolean remove(Integer id){
        if (id == null || eventoDAO.findOne(id).isEmpty())
            throw new EntityNotFoundException("Evento não foi encontrado.");
        return eventoDAO.deleteByKey(id);
    }
}
