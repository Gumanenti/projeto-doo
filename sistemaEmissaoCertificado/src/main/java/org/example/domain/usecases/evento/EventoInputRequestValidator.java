package org.example.domain.usecases.evento;

import org.example.domain.entities.evento.Evento;
import org.example.domain.usecases.utils.Notification;
import org.example.domain.usecases.utils.Validator;

import java.util.Collections;

public class EventoInputRequestValidator extends Validator<Evento> {
    @Override
    public Notification validate(Evento evento) {
        Notification notification = new Notification();

        if(nullOrEmpty(evento.getNome())) {
            notification.addError("Nome não existe ou está vazio.");
        }
        if(nullOrEmpty(Collections.singleton(evento.getData()))){
            notification.addError("Data não existe ou está vazio.");
        }
        if(nullOrEmpty(evento.getNomePalestrante())){
            notification.addError("Palestrante não existe ou está vazio.");
        }

        if(nullOrEmpty(evento.getPathTemplateImage())){
            notification.addError("Caminho do template não existe ou esstá vazio.");
        }

        return notification;
    }
}
