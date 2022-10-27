package org.example.domain.usecases.evento;

import org.example.domain.entities.evento.Evento;
import org.example.domain.entities.participante.Participante;
import org.example.domain.usecases.utils.DAO;

import java.util.Optional;

public interface EventoDAO extends DAO<Evento, Integer> {

    Optional<Evento> findById(Integer id);

    Optional<Evento> findOne(String key);

    boolean deleteByKey(String key);
}
