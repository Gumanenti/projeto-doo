package org.example.domain.usecases.administrador;

import org.example.domain.entities.administrador.Administrador;

import java.util.List;
import java.util.Optional;

public class FindAdministradorUseCase {
    private AdministradorDAO administradorDAO;

    public FindAdministradorUseCase(AdministradorDAO administradorDAO) {
        this.administradorDAO = administradorDAO;
    }

    public Optional<Administrador> findOne(String login){
        if (login == null) {
            throw new IllegalArgumentException("Não pode ser nulo.");
        }
        return administradorDAO.findOne(login);
    }

    public List<Administrador> findAll(){
        return administradorDAO.findAll();
    }

}
