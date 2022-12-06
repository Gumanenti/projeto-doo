package org.example.domain.entities.evento;

import java.sql.Date;
import java.time.LocalDateTime;

public class Evento {
    private Integer id;
    private String nome;
    private LocalDateTime data;
    private Integer cargaHoraria;
    private String nomePalestrante;
    private String pathTemplateImage;
    private EventoStatus eventoStatus;

    public Evento(){}

    public Evento(String nome, LocalDateTime data, Integer cargaHoraria, String nomePalestrante) {
        this(null, nome, data, cargaHoraria, nomePalestrante, null);
    }
    public Evento(Integer id, String nome, LocalDateTime data, Integer cargaHoraria, String nomePalestrante) {
        this(id, nome, data, cargaHoraria, nomePalestrante, null);
    }


    public Evento(Integer id, String nome, LocalDateTime data, Integer cargaHoraria, String nomePalestrante, String pathTemplateImage) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.cargaHoraria = cargaHoraria;
        this.nomePalestrante = nomePalestrante;
        this.pathTemplateImage = pathTemplateImage;
    }

    public Integer getId() {return id;}

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDateTime getData() {return data;}

    public void setData(LocalDateTime data) {this.data = data;}

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getNomePalestrante() {
        return nomePalestrante;
    }

    public void setNomePalestrante(String nomePalestrante) {
        this.nomePalestrante = nomePalestrante;
    }

    public String getPathTemplateImage() {
        return pathTemplateImage;
    }

    public void setPathTemplateImage(String pathTemplateImage) {
        this.pathTemplateImage = pathTemplateImage;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EventoStatus getEventoStatus() {
        return eventoStatus;
    }

    public void setEventoStatus(EventoStatus eventoStatus) {
        this.eventoStatus = eventoStatus;
    }

    public void mostrarDados(){
        System.out.println("Evento{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", data='" + data + '\'' +
                ", cargaHoraria=" + cargaHoraria +
                ", nomePalestrante='" + nomePalestrante + '\'' +
                ", pathTemplateImage='" + pathTemplateImage + '\'' +
                '}');
    }
}
