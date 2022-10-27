package org.example.domain.usecases.evento;

import org.example.domain.entities.evento.Evento;
import org.example.domain.usecases.utils.EntityNotFoundException;

public class RemoveEventoUseCase {

    private EventoDAO eventoDAO;

    public RemoveEventoUseCase(EventoDAO eventoDAO) {
        this.eventoDAO = eventoDAO;
    }

    public boolean remove(Integer id){
        if(id == null || eventoDAO.findOne(id).isEmpty())
            throw new EntityNotFoundException("Evento não encontrado");
        return eventoDAO.deleteByKey(id);
    }

    public boolean remove(Evento evento){
        if(evento == null || eventoDAO.findOne(evento.getId()).isEmpty())
            throw new EntityNotFoundException("Evento não encontrado");
        return eventoDAO.delete(evento);
    }
}
