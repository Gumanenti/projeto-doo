package org.example.domain.usecases.evento;

import org.example.domain.entities.evento.Evento;
import org.example.domain.usecases.utils.DAO;

import java.util.Optional;

public interface EventoDAO extends DAO<Evento, Integer> {
    Optional<Evento> findByNome(String nome);
}
