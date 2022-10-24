package org.example.domain.entities.certificado;

public class CertificadoStatus {

    private boolean validade;

    public CertificadoStatus(){}

    public CertificadoStatus(boolean validade) {
        this.validade = validade;
    }


    public boolean getValidade() {return validade;}

    public void setValidade(boolean validade) {this.validade = validade;}

    public String mostrarDados() {
        return "CertificadoStatus{" +
                "validade=" + validade +
                '}';
    }



}
