package org.example.application.main;

import java.util.Optional;

import org.example.application.repository.inMemoryAdministradorDAO;
import org.example.application.repository.inMemoryCertificadoDAO;
import org.example.application.repository.inMemoryEventoDAO;
import org.example.application.repository.inMemoryParticipanteDAO;
import org.example.domain.entities.administrador.Administrador;
import org.example.domain.entities.participante.Participante;
import org.example.domain.entities.certificado.Certificado;
import org.example.domain.entities.certificado.CertificadoStatus;
import org.example.domain.entities.evento.Evento;
import org.example.domain.usecases.administrador.*;
import org.example.domain.usecases.certificado.*;
import org.example.domain.usecases.evento.*;
import org.example.domain.usecases.participante.*;

public class Main {

    private static CreateAdministradorUseCase createAdministradorUseCase;
    private static UpdateAdministradorUseCase updateAdministradorUseCase;
    private static FindAdministradorUseCase findAdministradorUseCase;
    private static RemoveAdministradorUseCase removeAdministradorUseCase;
    private static LoginAdministradorUseCase loginAdministradorUseCase;

    private static CreateCertificadoUseCase createCertificadoUseCase;
    private static UpdateCertificadoUseCase updateCertificadoUseCase;
    private static FindCertificadoUseCase findCertificadoUseCase;
    private static RemoveCertificadoUseCase removeCertificadoUseCase;
    private static GeneratePDFCertificadoUseCase generatePDFCertificadoUseCase;

    private static CreateEventoUseCase createEventoUseCase;
    private static UpdateEventoUseCase updateEventoUseCase;
    private static FindEventoUseCase findEventoUseCase;
    private static RemoveEventoUseCase removeEventoUseCase;

    private static CreateParticipanteUseCase createParticipanteUseCase;
    private static UpdateParticipanteUseCase updateParticipanteUseCase;
    private static FindParticipanteUseCase findParticipanteUseCase;
    private static RemoveParticipanteUseCase removeParticipanteUseCase;

    public static void main(String[] args) {
        configureInjection();

        Administrador administrador = new Administrador("Danilo", "123", "Java");
        createAdministradorUseCase.insert(administrador);

        loginAdministradorUseCase.autentificarAdministrador("Danilo", "123");
        
        System.out.println(findAdministradorUseCase.findOne("Danilo").get().mostrarDados());

        //administrador.setLogin("Isa");
        administrador.setSenha("321");
        administrador.setPalavraChave("C");
        updateAdministradorUseCase.update(administrador);

        System.out.println(findAdministradorUseCase.findOne("Danilo").get().mostrarDados());

        removeAdministradorUseCase.remove("Danilo");

        if(findAdministradorUseCase.findOne("Danilo").isEmpty()){
            System.out.println("Apagado com sucesso!");
        }else {
            System.out.println("Error - Adminstrador não removido!");
        }

        Participante participante1 = new Participante("Gustavo", "gustavo@email.com", "12345678911");
        Participante participante2 = new Participante("Murilo", "murilo@email.com", "12345678912");

        createParticipanteUseCase.insert(participante1);
        createParticipanteUseCase.insert(participante2);

        checkPartipante(participante1);
        checkPartipante(participante2);

        findParticipanteUseCase.findOne(participante1.getCpf()).orElseThrow().mostrarDados();
        findParticipanteUseCase.findOne(participante2.getCpf()).orElseThrow().mostrarDados();

        participante1.setEmail("email@email.com");
        participante1.setNome("Lucas da Silva");

        updateParticipanteUseCase.update(participante1);


        findParticipanteUseCase.findOne(participante1.getCpf()).orElseThrow().mostrarDados();

        System.out.println("\n\n\n");

        /* Só para não remover

        removeParticipanteUseCase.remove("12345678911");

        List<Participante> participanteList = findParticipanteUseCase.findAll();
        for(Participante p : participanteList){
            p.mostrarDados();
        }
         */

        Evento evento1 = new Evento(1, "Tusca", "27/10/2022", 40, "Danilo");
        Evento evento2 = new Evento(3, "Passeata", "30/9/2023", 30, "Ivone");
        Evento evento3 = new Evento(2, "HTML introdução", "6/1/2023", 40, "Thiago");
        Evento evento4 = new Evento(5, "CSS Ao estremo", "27/12/2024", 40, "Endrik");

        createEventoUseCase.insert(evento1);
        createEventoUseCase.insert(evento2);
        createEventoUseCase.insert(evento3);
        createEventoUseCase.insert(evento4);

        checkEvento(evento1.getId());

        findEventoUseCase.findOne(1).orElseThrow().mostrarDados();

        evento1.setData("99/99/99");
        evento1.setNome("Pós Tusca");
        evento1.setCargaHoraria(25);
        evento1.setNomePalestrante("Gustavo");

        updateEventoUseCase.update(evento1);

        Evento tmp = findEventoUseCase.findOne(1).orElseThrow();

        tmp.mostrarDados();

        CertificadoStatus certificadoStatus1 = new CertificadoStatus(true);

        Certificado certificado1 = new Certificado(evento1, participante1, "1", certificadoStatus1);
        Certificado certificado2 = new Certificado(evento2, participante2, "2", certificadoStatus1);
        Certificado certificado3 = new Certificado(evento3, participante1, "3", certificadoStatus1);
        Certificado certificado4 = new Certificado(evento4, participante2, "4", certificadoStatus1);
        Certificado certificado5 = new Certificado(evento3, participante1, "5", certificadoStatus1);
        Certificado certificado6 = new Certificado(evento4, participante2, "6", certificadoStatus1);

        createCertificadoUseCase.insert(certificado1);
        createCertificadoUseCase.insert(certificado2);
        createCertificadoUseCase.insert(certificado3);
        createCertificadoUseCase.insert(certificado4);
        createCertificadoUseCase.insert(certificado5);
        createCertificadoUseCase.insert(certificado6);

        generatePDFCertificadoUseCase.generatePDF("1", "templates/templateJPG1.jpg");
        generatePDFCertificadoUseCase.generatePDF("2", "templates/templateJPG2.jpg");
        generatePDFCertificadoUseCase.generatePDF("3", "templates/templateJPG3.jpg");
        generatePDFCertificadoUseCase.generatePDF("4", "templates/templatePNG1.png");
        generatePDFCertificadoUseCase.generatePDF("5", "templates/templatePNG2.png");
        generatePDFCertificadoUseCase.generatePDF("6", "templates/templatePNG3.png");
        
    }

    public static void checkEvento(int id){
        try{
            findEventoUseCase.findOne(id);
            System.out.println("Evento foi cadastrado!");
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    public static void checkPartipante(String cpf){
        try{
            findParticipanteUseCase.findOne("cpf");
            System.out.println("Participante foi cadadtrado!");
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    public static void checkPartipante(Participante participante){
        try{
            findParticipanteUseCase.findOne(participante.getCpf());
            System.out.println("Participante foi cadadtrado!");
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }


    public static void checkCertificado(Certificado c){
        try{
            findCertificadoUseCase.findOne(String.valueOf(c.codigo));
            System.out.println("Certificado foi cadadtrado!");
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    public static void checkCertificado(String codigo){
        try{
            findCertificadoUseCase.findOne(codigo);
            System.out.println("Certificado foi cadadtrado!");
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }



    private static void configureInjection(){
        AdministradorDAO administradorDAO = new inMemoryAdministradorDAO();
        createAdministradorUseCase = new CreateAdministradorUseCase(administradorDAO);
        updateAdministradorUseCase = new UpdateAdministradorUseCase(administradorDAO);
        findAdministradorUseCase = new FindAdministradorUseCase(administradorDAO);
        removeAdministradorUseCase = new RemoveAdministradorUseCase(administradorDAO);
        loginAdministradorUseCase = new LoginAdministradorUseCase(administradorDAO);

        CertificadoDAO certificadoDAO = new inMemoryCertificadoDAO();
        createCertificadoUseCase = new CreateCertificadoUseCase(certificadoDAO);
        updateCertificadoUseCase = new UpdateCertificadoUseCase(certificadoDAO);
        findCertificadoUseCase = new FindCertificadoUseCase(certificadoDAO);
        removeCertificadoUseCase = new RemoveCertificadoUseCase(certificadoDAO);
        generatePDFCertificadoUseCase = new GeneratePDFCertificadoUseCase(certificadoDAO);

        EventoDAO eventoDAO = new inMemoryEventoDAO();
        createEventoUseCase = new CreateEventoUseCase(eventoDAO);
        updateEventoUseCase = new UpdateEventoUseCase(eventoDAO);
        findEventoUseCase = new FindEventoUseCase(eventoDAO);
        removeEventoUseCase = new RemoveEventoUseCase(eventoDAO);

        ParticipanteDAO participanteDAO = new inMemoryParticipanteDAO();
        createParticipanteUseCase = new CreateParticipanteUseCase(participanteDAO);
        updateParticipanteUseCase = new UpdateParticipanteUseCase(participanteDAO);
        findParticipanteUseCase = new FindParticipanteUseCase(participanteDAO);
        removeParticipanteUseCase = new RemoveParticipanteUseCase(participanteDAO);



    }
}
