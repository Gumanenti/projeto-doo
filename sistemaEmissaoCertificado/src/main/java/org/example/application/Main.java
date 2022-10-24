package org.example.application;

import org.example.domain.entities.participante.Participante;
import org.example.domain.entities.certificado.Certificado;
import org.example.domain.entities.certificado.CertificadoStatus;
import org.example.domain.entities.evento.Evento;

public class Main {
    public static void main(String[] args) {

        Evento evento1 = new Evento(1, "Palestra sobre Política", "2018-02-02", 40, "José");
        evento1.mostrarDados();

        Participante participante1 = new Participante("Joao", "joao@gmail.com", "3978872456");
        participante1.mostrarDados();

        CertificadoStatus certStatus1 = new CertificadoStatus(true);

        Certificado certificado1 = new Certificado(evento1, participante1, "cod0001", certStatus1);
        certificado1.mostrarDados();

        participante1.addCertificado(certificado1);
        participante1.mostrarDados();
    }
}
