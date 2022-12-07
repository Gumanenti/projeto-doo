package org.example.application.repository.inmemory;

import org.example.domain.entities.participante.Participante;
import org.example.domain.usecases.participante.ParticipanteDAO;

import java.util.*;

public class inMemoryParticipanteDAO implements ParticipanteDAO {

    private static final Map<String, Participante> db = new LinkedHashMap<>();

    @Override
    public String create(Participante participante) {
        db.put(participante.getCpf(), participante);
        return participante.getCpf();
    }

    @Override
    public Optional<Participante> findOne(String key) {
        if(db.containsKey(key)){
            return Optional.of(db.get(key));
        }
        return Optional.empty();
    }

    @Override
    public List<Participante> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public boolean update(Participante participante) {
        String cpf = participante.getCpf();
        if(db.containsKey(cpf)){
            db.replace(cpf, participante);
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
    public boolean delete(Participante participante) {
        return deleteByKey(participante.getCpf());
    }
}
