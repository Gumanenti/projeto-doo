package org.example.application.main;

import org.example.application.repository.inMemoryAdministradorDAO;
import org.example.application.repository.inMemoryCertificadoDAO;
import org.example.application.repository.inMemoryEventoDAO;
import org.example.application.repository.inMemoryParticipanteDAO;
import org.example.domain.entities.administrador.Administrador;
import org.example.domain.entities.certificado.Certificado;
import org.example.domain.entities.evento.Evento;
import org.example.domain.entities.participante.Participante;
import org.example.domain.usecases.administrador.*;
import org.example.domain.usecases.certificado.*;
import org.example.domain.usecases.evento.CreateEventoUseCase;
import org.example.domain.usecases.evento.EventoDAO;
import org.example.domain.usecases.evento.FindEventoUseCase;
import org.example.domain.usecases.evento.UpdateEventoUseCase;
import org.example.domain.usecases.participante.*;
import org.example.domain.usecases.participanteInEvento.AgroupDataEventAndParticipantUseCase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main extends Thread{

    private static CreateAdministradorUseCase createAdministradorUseCase;
    private static UpdateAdministradorUseCase updateAdministradorUseCase;
    private static FindAdministradorUseCase findAdministradorUseCase;
    private static RemoveAdministradorUseCase removeAdministradorUseCase;
    private static RequestAdministradorKeyWordUseCase requestAdministradorKeyWordUseCase;
    private static LoginAdministradorUseCase loginAdministradorUseCase;

    private static GenerateCertificadoUseCase generateCertificadoUseCase;
    private static UpdateCertificadoUseCase updateCertificadoUseCase;
    private static FindCertificadoUseCase findCertificadoUseCase;
    private static GeneratePDFCertificadoUseCase generatePDFCertificadoUseCase;
    private static GenerateHashCodeCertificadoUseCase generateHashCodeCertificadoUseCase;
    private static CreateEventoUseCase createEventoUseCase;
    private static UpdateEventoUseCase updateEventoUseCase;
    private static FindEventoUseCase findEventoUseCase;
    private static InvalidHashCodeCertificadoUseCase invalidHashCodeCertificadoUseCase;
    private static AgroupDataEventAndParticipantUseCase agroupDataEventAndParticipantUseCase;

    private static CreateParticipanteUseCase createParticipanteUseCase;
    private static UpdateParticipanteUseCase updateParticipanteUseCase;
    private static FindParticipanteUseCase findParticipanteUseCase;
    private static RemoveParticipanteUseCase removeParticipanteUseCase;
    private static AttachParticipantListUseCase attachParticipantListUseCase;

    public static void main(String[] args) throws InterruptedException {
        configureInjection();

        System.out.println("Caso de uso realizar Login : Fluxo normal");
        Administrador administrador = new Administrador("Danilo", "123", "Java");
        createAdministradorUseCase.insert(administrador);
        requestLogin("Danilo", "123");

        Thread.sleep(50);

        System.out.println("\nCaso de uso realizar Login : Fluxo alternativo, senha inválida. (Caso de uso Fornecer dicas de palavra passe inclusa)");
        requestLogin("Danilo", "1234");

        Thread.sleep(50);

        System.out.println("\nCaso de uso realizar Login : Fluxo alternativo, login inválida");
        requestLogin("Danilo2", "123");

        Thread.sleep(50);

        System.out.println("\nCaso de uso criar eventos : Fluxo normal");
        Integer idEvent = createEventoUseCase.createEvent("Aulão de HTML!", LocalDate.of(2023,01,01), 20, "Tião", "https://img.freepik.com/vetores-premium/borda-de-moldura-de-guilhoche-vermelha-classica-para-diploma-ou-certificado-vetor-a4-cor-cmyk-horizontal-as-camadas-sao-separadas-a-edicao-e-facil_638259-1241.jpg?w=2000");
        Evento e1 = findEvento(idEvent);
        e1.mostrarDados();

        Thread.sleep(50);

        System.out.println("\nCaso de uso criar eventos : Fluxo alternativo, evento já cadastrado!");
        try {
            idEvent = createEventoUseCase.createEvent("Aulão de HTML!", LocalDate.of(2023,01,01), 20, "Tião", "https://img.freepik.com/vetores-premium/borda-de-moldura-de-guilhoche-vermelha-classica-para-diploma-ou-certificado-vetor-a4-cor-cmyk-horizontal-as-camadas-sao-separadas-a-edicao-e-facil_638259-1241.jpg?w=2000");
        }catch (Exception e){
            System.err.println(e.getMessage());
        }

        Thread.sleep(50);

        System.out.println("\nCaso de uso Listar Participante : Fluxo normal");
        List<org.example.domain.entities.participante.Participante> participanteList = readCsvFile("participantes.csv");
        for (org.example.domain.entities.participante.Participante p : participanteList)
            p.mostrarDados();

        Thread.sleep(50);

        System.out.println("\nCaso de uso Listar Participante : Alternativo, arquivo não encontrado");
        participanteList.clear();
        participanteList = readCsvFile("participantes2.csv");
        for (org.example.domain.entities.participante.Participante p : participanteList)
            p.mostrarDados();

        Thread.sleep(50);

        System.out.println("\nCaso de uso Agrupar dados : Fluxo normal.");
        participanteList = readCsvFile("participantes.csv");
        agroupDataEventAndParticipantUseCase.agroupData("Aulão de HTML!", participanteList);
        System.out.println("Lista de Participantes no evento");
        for (Participante p : e1.getParticipanteList())
            p.mostrarDados();

        Thread.sleep(50);

        System.out.println("\nCaso de uso Agrupar dados : Fluxo alternativo, evento não cadastrado");
        try{
            agroupDataEventAndParticipantUseCase.agroupData("Aulão 2 de HTML!", participanteList);
        }  catch (Exception e){
            System.err.println(e.getMessage());
        }

        for (Participante p : participanteList)
            createParticipanteUseCase.insert(p);


        Thread.sleep(50);

        System.out.println("\nCaso de uso Gerar certificados : Fluxo normal");
        String hashCodeCertificado = "";
        try{
            hashCodeCertificado = generateCertificadoUseCase.createCertificado(e1.getId(), "12345678912");
            System.out.println("Código Hash: " + hashCodeCertificado);
        }  catch (Exception e){
            System.err.println(e.getMessage());
        }

        Thread.sleep(50);

        System.out.println("\nCaso de uso Gerar PDF certificado : Fluxo normal");
        generatePDF(hashCodeCertificado);
        checkCertificado(hashCodeCertificado);

        Thread.sleep(50);

        System.out.println("\nCaso de uso inválidar certificado : Fluxo normal");
        invalidHashCodeCertificadoUseCase.invalidCertificado(hashCodeCertificado);
        checkCertificado(hashCodeCertificado);

    }

    public static void requestLogin(Administrador admin){
        requestLogin(admin.getLogin(), admin.getSenha());
    }

    public static void requestLogin(String login, String senha){
        try{
            loginAdministradorUseCase.autentificarAdministrador(login, senha);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    public static List<org.example.domain.entities.participante.Participante> readCsvFile(String filePath){
        List<org.example.domain.entities.participante.Participante> participanteList = new ArrayList<>();
        try{
            participanteList = attachParticipantListUseCase.attachCsvFile(filePath);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return participanteList;
    }

    public static Evento findEvento(int id){
        try{
            return findEventoUseCase.findOne(id).get();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static void checkCertificado(String codigo){
        try{
            Certificado c;
            if (findCertificadoUseCase.findOne(codigo).isPresent())
                System.out.println("Certificado foi cadastrado!");
            else
                System.out.println("Certificado não existe!");

        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    public static void generatePDF(String code){
        try{
            generatePDFCertificadoUseCase.generatePDF(code);
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
        requestAdministradorKeyWordUseCase = new RequestAdministradorKeyWordUseCase(administradorDAO);
        loginAdministradorUseCase = new LoginAdministradorUseCase(administradorDAO, requestAdministradorKeyWordUseCase);

        EventoDAO eventoDAO = new inMemoryEventoDAO();
        createEventoUseCase = new CreateEventoUseCase(eventoDAO);
        updateEventoUseCase = new UpdateEventoUseCase(eventoDAO);
        findEventoUseCase = new FindEventoUseCase(eventoDAO);
        agroupDataEventAndParticipantUseCase = new AgroupDataEventAndParticipantUseCase(eventoDAO);

        ParticipanteDAO participanteDAO = new inMemoryParticipanteDAO();
        createParticipanteUseCase = new CreateParticipanteUseCase(participanteDAO);
        updateParticipanteUseCase = new UpdateParticipanteUseCase(participanteDAO);
        findParticipanteUseCase = new FindParticipanteUseCase(participanteDAO);
        removeParticipanteUseCase = new RemoveParticipanteUseCase(participanteDAO);
        attachParticipantListUseCase = new AttachParticipantListUseCase();

        CertificadoDAO certificadoDAO = new inMemoryCertificadoDAO();
        generatePDFCertificadoUseCase = new GeneratePDFCertificadoUseCase(certificadoDAO);
        generateHashCodeCertificadoUseCase = new  GenerateHashCodeCertificadoUseCase(certificadoDAO);
        generateCertificadoUseCase = new GenerateCertificadoUseCase(certificadoDAO, findParticipanteUseCase, findEventoUseCase, generatePDFCertificadoUseCase, generateHashCodeCertificadoUseCase);
        invalidHashCodeCertificadoUseCase = new InvalidHashCodeCertificadoUseCase(certificadoDAO);
        updateCertificadoUseCase = new UpdateCertificadoUseCase(certificadoDAO);
        findCertificadoUseCase = new FindCertificadoUseCase(certificadoDAO);

    }
}
