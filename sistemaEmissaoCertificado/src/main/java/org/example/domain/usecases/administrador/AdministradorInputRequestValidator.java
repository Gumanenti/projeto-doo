package org.example.domain.usecases.administrador;

import org.example.domain.entities.administrador.Administrador;
import org.example.domain.entities.participante.Participante;
import org.example.domain.usecases.utils.Notification;
import org.example.domain.usecases.utils.Validator;

public class AdministradorInputRequestValidator extends Validator<Administrador> {
    @Override
    public Notification validate(Administrador administrador) {
        Notification notification = new Notification();

        if(administrador == null){
            notification.addError("Administrador não existe");
            return notification;
        }
        if(nullOrEmpty(administrador.getLogin())) {
            notification.addError("Login não existe ou está vazio");
        }
        if(nullOrEmpty(administrador.getSenha())){
            notification.addError("Senha não existe ou está vazia");
        }
        if(nullOrEmpty(administrador.getPalavraChave())){
            notification.addError("Palavra-chave não existe ou está vazio");
        }

        return notification;
    }
}
