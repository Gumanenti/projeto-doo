package org.example.application.main;

import org.example.application.repository.sqlite.*;
import org.example.application.view.WindowLoader;
import org.example.domain.entities.certificado.Certificado;
import org.example.domain.entities.participante.Participante;
import org.example.domain.usecases.administrador.*;
import org.example.domain.usecases.certificado.*;
import org.example.domain.usecases.evento.*;
import org.example.domain.usecases.participante.*;

import java.util.ArrayList;
import java.util.List;

public class Main extends Thread{

    public static CreateAdministradorUseCase createAdministradorUseCase;
    public static UpdateAdministradorUseCase updateAdministradorUseCase;
    public static FindAdministradorUseCase findAdministradorUseCase;
    public static RemoveAdministradorUseCase removeAdministradorUseCase;
    public static RequestAdministradorKeyWordUseCase requestAdministradorKeyWordUseCase;
    public static LoginAdministradorUseCase loginAdministradorUseCase;
    public static GenerateCertificadoUseCase generateCertificadoUseCase;
    public static UpdateCertificadoUseCase updateCertificadoUseCase;
    public static FindCertificadoUseCase findCertificadoUseCase;
    public static GeneratePDFCertificadoUseCase generatePDFCertificadoUseCase;
    public static GenerateHashCodeCertificadoUseCase generateHashCodeCertificadoUseCase;
    public static RegenerateCertificadoUseCase regenerateCertificadoUseCase;
    public static SendCertificateByEmailUseCase sendCertificateByEmailUseCase;

    public static CreateEventoUseCase createEventoUseCase;
    public static UpdateEventoUseCase updateEventoUseCase;
    public static FindEventoUseCase findEventoUseCase;
    public static RemoveEventoUseCase removeEventoUseCase;

    public static InvalidHashCodeCertificadoUseCase invalidHashCodeCertificadoUseCase;
    public static CreateParticipanteUseCase createParticipanteUseCase;
    public static UpdateParticipanteUseCase updateParticipanteUseCase;
    public static FindParticipanteUseCase findParticipanteUseCase;
    public static RemoveParticipanteUseCase removeParticipanteUseCase;
    public static AttachParticipantListUseCase attachParticipantListUseCase;
    public static CheckCertificadoValidatedHashCodeUseCase checkCertificadoValidatedHashCodeUseCase;
    public static List<Participante> participanteToGenerateCertificate;
    public static List<Certificado> certificateListToSendEmail = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        configureInjection();
        setupDatabase();
        WindowLoader.main(args);
    }

    private static void setupDatabase() {
        DatabaseBuilder databaseBuilder = new DatabaseBuilder();
        databaseBuilder.buildDatabaseIfMissing();
    }

    private static void configureInjection(){
        AdministradorDAO administradorDAO = new SqliteAdministradorDAO();
        createAdministradorUseCase = new CreateAdministradorUseCase(administradorDAO);
        updateAdministradorUseCase = new UpdateAdministradorUseCase(administradorDAO);
        findAdministradorUseCase = new FindAdministradorUseCase(administradorDAO);
        removeAdministradorUseCase = new RemoveAdministradorUseCase(administradorDAO);
        requestAdministradorKeyWordUseCase = new RequestAdministradorKeyWordUseCase(administradorDAO);
        loginAdministradorUseCase = new LoginAdministradorUseCase(administradorDAO, requestAdministradorKeyWordUseCase);

        EventoDAO eventoDAO = new SqliteEventoDAO();
        createEventoUseCase = new CreateEventoUseCase(eventoDAO);
        updateEventoUseCase = new UpdateEventoUseCase(eventoDAO);
        findEventoUseCase = new FindEventoUseCase(eventoDAO);
        removeEventoUseCase = new RemoveEventoUseCase(eventoDAO);

        ParticipanteDAO participanteDAO = new SqlitteParticipanteDAO();
        createParticipanteUseCase = new CreateParticipanteUseCase(participanteDAO);
        updateParticipanteUseCase = new UpdateParticipanteUseCase(participanteDAO);
        findParticipanteUseCase = new FindParticipanteUseCase(participanteDAO);
        removeParticipanteUseCase = new RemoveParticipanteUseCase(participanteDAO);
        attachParticipantListUseCase = new AttachParticipantListUseCase();

        CertificadoDAO certificadoDAO = new SqliteCertificadoDAO();
        String pathRelatorios = "relatorios/";
        generatePDFCertificadoUseCase = new GeneratePDFCertificadoUseCase(certificadoDAO, pathRelatorios);
        generateHashCodeCertificadoUseCase = new  GenerateHashCodeCertificadoUseCase(certificadoDAO);
        invalidHashCodeCertificadoUseCase = new InvalidHashCodeCertificadoUseCase(certificadoDAO, pathRelatorios);
        updateCertificadoUseCase = new UpdateCertificadoUseCase(certificadoDAO);
        findCertificadoUseCase = new FindCertificadoUseCase(certificadoDAO);
        checkCertificadoValidatedHashCodeUseCase = new CheckCertificadoValidatedHashCodeUseCase(certificadoDAO);
        generateCertificadoUseCase = new GenerateCertificadoUseCase(certificadoDAO, findParticipanteUseCase, findEventoUseCase, generatePDFCertificadoUseCase, generateHashCodeCertificadoUseCase);
        regenerateCertificadoUseCase = new RegenerateCertificadoUseCase(certificadoDAO, generateCertificadoUseCase, pathRelatorios);
        sendCertificateByEmailUseCase = new SendCertificateByEmailUseCase(findCertificadoUseCase);

    }
}
