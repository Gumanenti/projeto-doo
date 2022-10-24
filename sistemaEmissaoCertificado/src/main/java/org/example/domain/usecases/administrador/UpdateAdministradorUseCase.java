package org.example.domain.usecases.administrador;

import org.example.domain.entities.administrador.Administrador;
import org.example.domain.entities.participante.Participante;
import org.example.domain.usecases.utils.EntityNotFoundException;
import org.example.domain.usecases.utils.Notification;
import org.example.domain.usecases.utils.Validator;

public class UpdateAdministradorUseCase {
    private AdministradorDAO administradorDAO;

    public UpdateAdministradorUseCase(AdministradorDAO administradorDAO) {
        this.administradorDAO = administradorDAO;
    }

    public boolean update(Administrador administrador){
        Validator<Administrador> validator = new AdministradorInputRequestValidator();
        Notification notification = validator.validate(administrador);

        if(notification.hasErrors()){
            throw new IllegalArgumentException(notification.errorMessage());
        }

        String login = administrador.getLogin(); //Ctrl + Alt + V
        if(administradorDAO.findOne(login).isEmpty()){
            throw new EntityNotFoundException("Administrador não encontrado.");
        }

        return administradorDAO.update(administrador);
    }
}
