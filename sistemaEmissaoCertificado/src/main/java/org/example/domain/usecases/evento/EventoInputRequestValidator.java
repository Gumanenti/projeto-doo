package org.example.domain.usecases.evento;

import org.example.domain.entities.evento.Evento;
import org.example.domain.entities.participante.Participante;
import org.example.domain.usecases.utils.Notification;
import org.example.domain.usecases.utils.Validator;

public class EventoInputRequestValidator extends Validator<Evento> {
    @Override
    public Notification validate(Evento evento) {
        Notification notification = new Notification();

        if(evento == null){
            notification.addError("Evento não existe");
            return notification;
        }
        if(nullOrEmpty(evento.getNome())) {
            notification.addError("Nome não existe ou está vazio");
        }
        if(nullOrEmpty(evento.getData())){
            notification.addError("Data não existe ou está vazio");
        }
        if(nullOrEmpty(evento.getNomePalestrante())){
            notification.addError("Palestrante não existe ou está vazio");
        }

        return notification;
    }
}
