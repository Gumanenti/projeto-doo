package org.example.application.repository.inmemory;

import org.example.domain.entities.administrador.Administrador;
import org.example.domain.usecases.administrador.AdministradorDAO;

import java.util.*;

public class inMemoryAdministradorDAO implements AdministradorDAO {

    private static final Map<String, Administrador> db = new LinkedHashMap<>();

    @Override
    public String create(Administrador administrador) {
        db.put(administrador.getLogin(), administrador);
        return administrador.getLogin();
    }

    @Override
    public Optional<Administrador> findOne(String key) {
        if(db.containsKey(key)){
            return Optional.of(db.get(key));
        }
        return Optional.empty();
    }

    @Override
    public List<Administrador> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public boolean update(Administrador administrador) {
        String login = administrador.getLogin();
        if(db.containsKey(login)){
            db.replace(login, administrador);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteByKey(String key) {
        if(db.containsKey(key)){
            db.remove(key);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Administrador administrador) {
        return deleteByKey(administrador.getLogin());
    }
}
