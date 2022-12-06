package org.example.domain.usecases.participanteInEvento;

import org.example.domain.entities.evento.Evento;
import org.example.domain.entities.participante.Participante;
import org.example.domain.usecases.certificado.GenerateCertificadoUseCase;
import org.example.domain.usecases.evento.EventoDAO;
import org.example.domain.usecases.participante.CreateParticipanteUseCase;
import org.example.domain.usecases.participante.FindParticipanteUseCase;
import org.example.domain.usecases.participante.UpdateParticipanteUseCase;

import java.util.List;
import java.util.Optional;

public class AgroupDataEventAndParticipantUseCase {
    private EventoDAO eventoDAO;
    private CreateParticipanteUseCase createParticipanteUseCase;
    private FindParticipanteUseCase findParticipanteUseCase;
    private UpdateParticipanteUseCase updateParticipanteUseCase;
    private GenerateCertificadoUseCase generateCertificadoUseCase;

    public AgroupDataEventAndParticipantUseCase(EventoDAO eventoDAO) {
        this.eventoDAO = eventoDAO;
    }


    public void agroupData(String eventName, List<Participante> participanteList){

        if (eventName == null || eventName.isEmpty())
            throw new IllegalArgumentException("Nome do evento não pode ser vazio ou nulo.");

        if (participanteList == null)
            throw new IllegalArgumentException("Lista de participantes não pode estar nula.");

        Optional<Evento> evento;
        evento = eventoDAO.findByNome(eventName);

        if (evento.isEmpty())
            throw new RuntimeException("Evento não existe!");

        eventoDAO.update(evento.get());
    }
}
