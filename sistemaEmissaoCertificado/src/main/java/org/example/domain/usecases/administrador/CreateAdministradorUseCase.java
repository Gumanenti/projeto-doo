package org.example.domain.usecases.administrador;

import org.example.domain.entities.administrador.Administrador;
import org.example.domain.entities.participante.Participante;
import org.example.domain.usecases.utils.EntityAlreadyExistsException;
import org.example.domain.usecases.utils.Notification;
import org.example.domain.usecases.utils.Validator;

public class CreateAdministradorUseCase {

    private AdministradorDAO administradorDAO;

    public CreateAdministradorUseCase(AdministradorDAO administradorDAO) {
        this.administradorDAO = administradorDAO;
    }

    public String insert(Administrador administrador){
        Validator<Administrador> validator = new AdministradorInputRequestValidator();
        Notification notification = validator.validate(administrador);

        if(notification.hasErrors()){
            throw new IllegalArgumentException(notification.errorMessage());
        }

        String login = administrador.getLogin(); //Ctrl + Alt + V
        if(administradorDAO.findByLogin(login).isPresent()){
            throw new EntityAlreadyExistsException("Esse Login já está em uso.");
        }

        return administradorDAO.create(administrador);
    }
}
