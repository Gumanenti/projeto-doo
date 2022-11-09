package org.example.domain.usecases.evento;

import org.example.domain.entities.evento.Evento;
import org.example.domain.usecases.participante.AttachParticipantListUseCase;
import org.example.domain.usecases.utils.EntityAlreadyExistsException;
import org.example.domain.usecases.utils.Notification;
import org.example.domain.usecases.utils.Validator;

import java.time.LocalDate;

public class CreateEventoUseCase {
    private EventoDAO eventoDAO;

    public CreateEventoUseCase(EventoDAO eventoDAO, AttachParticipantListUseCase attachParticipantListUseCase) {
        this.eventoDAO = eventoDAO;
    }

    public CreateEventoUseCase(EventoDAO eventoDAO) {
        this.eventoDAO = eventoDAO;
    }

    public Integer createEvent(String nome, LocalDate date, float cargaHoraria, String nomePalestrante, String pathTemplateImage){
        Evento evento = new Evento(null, nome, date, cargaHoraria, nomePalestrante, pathTemplateImage);

        return insert(evento);
    }

    private Integer insert(Evento evento){
        Validator<Evento> validator = new EventoInputRequestValidator();
        Notification notification = validator.validate(evento);
        Integer id = null;

        if(notification.hasErrors()){
            throw new IllegalArgumentException(notification.errorMessage());
        }

        String eventoNome = evento.getNome();
        if(eventoDAO.findByNome(eventoNome).isPresent()){
            throw new EntityAlreadyExistsException("Esse nome de evento já está em uso.");
        }

        id = eventoDAO.create(evento);

        return id;
    }
}
