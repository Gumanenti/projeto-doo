package org.example.application.repository;

import org.example.domain.entities.evento.Evento;
import org.example.domain.usecases.evento.EventoDAO;

import java.util.*;

public class inMemoryEventoDAO implements EventoDAO {

    private static final Map<String, Evento> db = new LinkedHashMap<>();
    private static int idCounter; //precisa criar o id dentro da classe

    @Override
    public Optional<Evento> findById(Integer id) {
        return db.values().stream()
                .filter(evento -> Objects.equals(evento.getId(), id))
                .findAny();
    }

    @Override
    public Optional<Evento> findOne(String key) {
        if(db.containsKey(key)){
            return Optional.of(db.get(key));
        }
        return Optional.empty();
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
    public Integer create(Evento evento) {
        idCounter++;
        evento.setId(idCounter);
        db.put(String.valueOf(idCounter), evento);
        return idCounter;
    }


    @Override
    public Optional<Evento> findOne(Integer key) {
        if(db.containsKey(key)){
            return Optional.of(db.get(key));
        }
        return Optional.empty();
    }

    @Override
    public List<Evento> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public boolean update(Evento evento) {
        Integer id = evento.getId();
        if(db.containsKey(id)){
            db.replace(String.valueOf(id), evento);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteByKey(Integer key) {
        if(db.containsKey(key)){
            db.remove(key);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Evento evento) {
        return deleteByKey(evento.getId());
    }
}
