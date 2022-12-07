package org.example.domain.entities.certificado;

public enum CertificadoStatus {
    VALID("Válido"),
    INVALID("Inválido");

    public final String label;

    private CertificadoStatus(String label){
        this.label = label;
    }
}
