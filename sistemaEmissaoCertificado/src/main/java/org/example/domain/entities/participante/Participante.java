package org.example.domain.entities.participante;

import org.example.domain.entities.certificado.Certificado;

import java.util.ArrayList;
import java.util.List;

public class Participante {
    private String nome;
    private String email;
    private String cpf;
    private List<Certificado> certificadoList;

    public Participante(String nome, String email, String cpf) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.certificadoList = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {return cpf;}

    public void setCpf(String cpf) {this.cpf = cpf;}

    public List<Certificado> getCertificadoList(){
        return this.certificadoList;
    }

    public void addCertificado(Certificado certificado){
        certificadoList.add(certificado);
    }

    public void mostrarDados(){
        System.out.println("Participante{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", cpf='" + cpf + '\'' +
                '}');
    }

}
