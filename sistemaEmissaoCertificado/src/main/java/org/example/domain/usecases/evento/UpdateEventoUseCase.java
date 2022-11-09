package org.example.domain.usecases.evento;

import org.example.domain.entities.evento.Evento;
import org.example.domain.usecases.utils.EntityNotFoundException;
import org.example.domain.usecases.utils.Notification;
import org.example.domain.usecases.utils.Validator;

public class UpdateEventoUseCase {
    private EventoDAO eventoDAO;

    public UpdateEventoUseCase(EventoDAO eventoDAO) {
        this.eventoDAO = eventoDAO;
    }

    public boolean update(Evento evento){
        Validator<Evento> validator = new EventoInputRequestValidator();
        Notification notification = validator.validate(evento);

        if(notification.hasErrors()){
            throw new IllegalArgumentException(notification.errorMessage());
        }

        Integer idEvento = evento.getId(); //Ctrl + Alt + V
        if(eventoDAO.findOne(idEvento).isEmpty()){
            throw new EntityNotFoundException("Evento não encontrado.");
        }

        return eventoDAO.update(evento);
    }
}
