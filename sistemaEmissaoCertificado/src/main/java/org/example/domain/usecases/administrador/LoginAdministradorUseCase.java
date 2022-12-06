package org.example.domain.usecases.administrador;

import org.example.domain.entities.administrador.Administrador;
import org.example.domain.usecases.utils.EntityNotFoundException;

import java.util.Optional;

public class LoginAdministradorUseCase {

    private AdministradorDAO administradorDAO;
    private RequestAdministradorKeyWordUseCase requestAdministradorKeyWordUseCase;

    public LoginAdministradorUseCase(AdministradorDAO administradorDAO, RequestAdministradorKeyWordUseCase requestAdministradorKeyWordUseCase) {
        this.administradorDAO = administradorDAO;
        this.requestAdministradorKeyWordUseCase = requestAdministradorKeyWordUseCase;
    }

    public boolean autentificarAdministrador(String login, String senha){

        if (login == null ||  login.isEmpty() || senha == null || login.isEmpty()) {
            throw new IllegalArgumentException("Login e/ou senha não podem ser vazios ou nulos.");
        }
        Optional<Administrador> admin;
        admin = administradorDAO.findOne(login);

        if(admin.isEmpty())
            throw new EntityNotFoundException("Não foi possivel fazer o login. Login não encontrado");


        if(!admin.get().getSenha().equals(senha))
            requestAdministradorKeyWordUseCase.getKeyWord(login);

        System.out.println("Login e senha corretos. Entrando no sistema...");
        return true;
    }
}
