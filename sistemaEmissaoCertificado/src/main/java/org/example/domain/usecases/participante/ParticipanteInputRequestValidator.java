package org.example.domain.usecases.participante;

import org.example.domain.entities.participante.Participante;
import org.example.domain.usecases.utils.Notification;
import org.example.domain.usecases.utils.Validator;

public class ParticipanteInputRequestValidator extends Validator<Participante> {
    @Override
    public Notification validate(Participante participante) {
        Notification notification = new Notification();

        if(participante == null){
            notification.addError("Participante não existe");
            return notification;
        }
        if(nullOrEmpty(participante.getCpf())) {
            notification.addError("Participante não existe ou está vazio");
        }
        if(nullOrEmpty(participante.getNome())){
            notification.addError("Nome não existe ou está vazio");
        }
        if(nullOrEmpty(participante.getEmail())){
            notification.addError("E-mail não existe ou está vazio");
        }

        return notification;
    }
}
