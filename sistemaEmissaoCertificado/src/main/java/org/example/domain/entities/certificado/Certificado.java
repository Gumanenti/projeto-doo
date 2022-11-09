package org.example.domain.entities.certificado;

import org.example.domain.entities.evento.Evento;

public class Certificado {
    private Evento evento;
    private org.example.domain.entities.participante.Participante participante;
    private String codigo;
    private CertificadoStatus certificadoStatus;
    public Certificado() {
    }

    public Certificado(Evento evento, org.example.domain.entities.participante.Participante participante, CertificadoStatus certificadoStatus){
        this(evento, participante,null, certificadoStatus);
    }

    public Certificado(Evento evento, org.example.domain.entities.participante.Participante participante, String codigo, CertificadoStatus certificadoStatus){
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

    public org.example.domain.entities.participante.Participante getParticipante() {
        return participante;
    }

    public void setParticipante(org.example.domain.entities.participante.Participante participante) {
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
