package org.example.domain.usecases.administrador;

import org.example.domain.entities.administrador.Administrador;
import org.example.domain.usecases.utils.EntityNotFoundException;

import java.util.Optional;

public class LoginAdministradorUseCase {

    private AdministradorDAO administradorDAO;

    public LoginAdministradorUseCase(AdministradorDAO administradorDAO) {this.administradorDAO = administradorDAO;}

    public void autentificarAdministrador(String login, String senha){
        Optional<Administrador> administrador;
        administrador = administradorDAO.findOne(login);

        if(administrador.isEmpty()){
            throw new EntityNotFoundException("Não foi possivel fazer o login. Administrador não encontrado");
        }

        if(administrador.get().getSenha() != senha){
            throw new EntityNotFoundException("Senha incorreta. Palavra-chave:"+ administrador.get().getPalavraChave());
        }

        System.out.println("Login e senha corretos. Entrando no sistema...");
    }
}
