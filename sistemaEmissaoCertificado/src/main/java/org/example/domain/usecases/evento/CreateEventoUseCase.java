package org.example.domain.usecases.evento;

import org.example.domain.entities.evento.Evento;
import org.example.domain.entities.participante.Participante;
import org.example.domain.usecases.utils.EntityAlreadyExistsException;
import org.example.domain.usecases.utils.Notification;
import org.example.domain.usecases.utils.Validator;

public class CreateEventoUseCase {

    private EventoDAO eventoDAO;

    public CreateEventoUseCase(EventoDAO eventoDAO) {
        this.eventoDAO = eventoDAO;
    }

    public Integer insert(Evento evento){
        Validator<Evento> validator = new EventoInputRequestValidator();
        Notification notification = validator.validate(evento);

        if(notification.hasErrors()){
            throw new IllegalArgumentException(notification.errorMessage());
        }

        Integer idEvento = evento.getId(); //Ctrl + Alt + V
        if(eventoDAO.findById(idEvento).isPresent()){
            throw new EntityAlreadyExistsException("Esse ID já está em uso.");
        }

        return eventoDAO.create(evento);
    }
}
