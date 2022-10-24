package org.example.domain.usecases.administrador;

import org.example.domain.entities.administrador.Administrador;
import org.example.domain.entities.participante.Participante;
import org.example.domain.usecases.utils.EntityNotFoundException;

public class RemoveAdministradorUseCase {

    private AdministradorDAO administradorDAO;

    public RemoveAdministradorUseCase(AdministradorDAO administradorDAO) {
        this.administradorDAO = administradorDAO;
    }

    public boolean remove(String login){
        if(login == null || administradorDAO.findOne(login).isEmpty())
            throw new EntityNotFoundException("Administrador não encontrado");
        return administradorDAO.deleteByKey(login);
    }

    public boolean remove(Administrador administrador){
        if(administrador == null || administradorDAO.findOne(administrador.getLogin()).isEmpty())
            throw new EntityNotFoundException("Administrador não encontrado");
        return administradorDAO.delete(administrador);
    }
}
