package org.example.domain.entities.evento;

import java.time.LocalDate;

public class Evento {

    private int id;
    private String nome;
    private String data;
    private float cargaHoraria;
    private String nomePalestrante;

    public Evento(){}

    public Evento(int id, String nome, String data, float cargaHoraria, String nomePalestrante) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.cargaHoraria = cargaHoraria;
        this.nomePalestrante = nomePalestrante;
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

    public String getData() {return data;}

    public void setData(String data) {this.data = data;}

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

    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", data='" + data + '\'' +
                ", cargaHoraria=" + cargaHoraria +
                ", nomePalestrante='" + nomePalestrante + '\'' +
                '}';
    }

    public void mostrarDados(){
        System.out.println("Evento{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", data='" + data + '\'' +
                ", cargaHoraria=" + cargaHoraria +
                ", nomePalestrante='" + nomePalestrante + '\'' +
                '}');
    }
}
