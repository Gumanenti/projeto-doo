package org.example.domain.entities.certificado;

import org.example.domain.entities.participante.Participante;
import org.example.domain.entities.evento.Evento;

public class Certificado {

    private Evento evento;
    private Participante participante;
    public String codigo;
    private CertificadoStatus certificadoStatus;

    public Certificado() {
    }

    public Certificado(Evento evento, Participante participante, String codigo, CertificadoStatus certificadoStatus) {
        this.evento = evento;
        this.participante = participante;
        this.codigo = codigo;
        this.certificadoStatus = certificadoStatus;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public CertificadoStatus getCertificadoStatus() {
        return certificadoStatus;
    }

    public void setCertificadoStatus(CertificadoStatus certificadoStatus) {
        this.certificadoStatus = certificadoStatus;
    }

    public void mostrarDados() {
        System.out.println("Certificado{" +
                "evento=" + evento.getNome() +
                ", participante=" + participante.getNome() +
                ", codigo='" + codigo + '\'' +
                ", certificadoStatus=" + certificadoStatus.mostrarDados() +
                '}');
    }

}
