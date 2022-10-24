package org.example.domain.entities.evento;

public class EventoStatus {

    private int quantidade;

    public EventoStatus(){}

    public EventoStatus(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
