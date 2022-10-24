package org.example.domain.usecases.participante;

import org.example.domain.entities.certificado.Certificado;
import org.example.domain.entities.participante.Participante;
import org.example.domain.usecases.utils.DAO;

import java.util.Optional;

public interface ParticipanteDAO extends DAO<Participante, String> {
    Optional<Participante> findByCPF(String cpf);
}
