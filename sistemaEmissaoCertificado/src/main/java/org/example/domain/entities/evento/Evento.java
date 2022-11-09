package org.example.domain.entities.evento;

import org.example.domain.entities.participante.Participante;

import java.time.LocalDate;
import java.util.List;

public class Evento {
    private Integer id;
    private String nome;
    private LocalDate data;
    private float cargaHoraria;
    private String nomePalestrante;
    private String pathTemplateImage;
    private List<Participante> participanteList;

    public Evento(){}

    public Evento(String nome, LocalDate data, float cargaHoraria, String nomePalestrante) {
        this(null, nome, data, cargaHoraria, nomePalestrante, null, null);
    }
    public Evento(Integer id, String nome, LocalDate data, float cargaHoraria, String nomePalestrante) {
        this(id, nome, data, cargaHoraria, nomePalestrante, null, null);
    }

    public Evento(Integer id, String nome, LocalDate data, float cargaHoraria, String nomePalestrante, String pathTemplateImage) {
        this(id, nome, data, cargaHoraria, nomePalestrante, pathTemplateImage, null);
    }

    public Evento(Integer id, String nome, LocalDate data, float cargaHoraria, String nomePalestrante, String pathTemplateImage, List<Participante> participanteList) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.cargaHoraria = cargaHoraria;
        this.nomePalestrante = nomePalestrante;
        this.pathTemplateImage = pathTemplateImage;
        this.participanteList = participanteList;
    }

    public int getId() {return id;}

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getData() {return data;}

    public void setData(LocalDate data) {this.data = data;}

    public float getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(float cargaHoraria) {
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

    public List<Participante> getParticipanteList() {
        return participanteList;
    }

    public void setParticipanteList(List<Participante> participanteList) {
        this.participanteList = participanteList;
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
