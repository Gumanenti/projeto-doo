package org.example.domain.usecases.participante;

public class ParticipanteNotAllowedException extends RuntimeException{
    public ParticipanteNotAllowedException(String message) {
        super(message);
    }
}
