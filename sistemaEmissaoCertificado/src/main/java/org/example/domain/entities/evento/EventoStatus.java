package org.example.domain.entities.evento;

public class EventoStatus {

    private Enum<EventoStausEnum> status;

    public EventoStatus(Enum<EventoStausEnum> status) {
        this.status = status;
    }

    public Enum<EventoStausEnum> getStatus(){
        return status;
    }
}