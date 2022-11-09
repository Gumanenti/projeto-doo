package org.example.domain.usecases.administrador;

import org.example.domain.entities.administrador.Administrador;
import org.example.domain.usecases.utils.DAO;

import java.util.Optional;

public interface AdministradorDAO extends DAO<Administrador, String> {
    Optional<Administrador> findByLogin(String login);
}
