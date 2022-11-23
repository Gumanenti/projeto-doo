package org.example.application.repository.inmemory;

import org.example.domain.entities.evento.Evento;
import org.example.domain.usecases.evento.EventoDAO;

import java.util.*;

public class inMemoryEventoDAO implements EventoDAO {

    private static final Map<Integer, Evento> db = new LinkedHashMap<>();
    private static int idCounter;

    @Override
    public Optional<Evento> findById(Integer id) {
        return db.values().stream()
                .filter(evento -> Objects.equals(evento.getId(), id))
                .findAny();
    }

    @Override
    public Optional<Evento> findByNome(String nome) {
        return db.values().stream()
                .filter(evento -> Objects.equals(evento.getNome(), nome))
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
        db.put(idCounter, evento);
        Integer id = evento.getId();
        db.put(id, evento);
        return evento.getId();
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
            db.replace(id, evento);
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
