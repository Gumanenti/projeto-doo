package org.example.domain.usecases.administrador;

import org.example.domain.entities.administrador.Administrador;
import org.example.domain.usecases.utils.EntityNotFoundException;

import java.util.Optional;

public class RequestAdministradorKeyWordUseCase {
    private AdministradorDAO administradorDAO;

    public RequestAdministradorKeyWordUseCase(AdministradorDAO administradorDAO) {
        this.administradorDAO = administradorDAO;
    }

    public void getKeyWord(String login){

        if (login == null ||  login.isEmpty())
            throw new IllegalArgumentException("Login senha não pode ser vazio ou nulo.");

        Optional<Administrador> administrador;
        administrador = administradorDAO.findOne(login);

        if(administrador.isEmpty()){
            throw new EntityNotFoundException("Não foi possivel fazer o login. Administrador não encontrado");
        }

        throw new EntityNotFoundException("Não foi possivel fazer login, pois senha é inválida! Palavra passe é: " + administrador.get().getPalavraChave());
    };
}
