package org.example.application.main;

import org.example.application.repository.inmemory.inMemoryAdministradorDAO;
import org.example.application.repository.inmemory.inMemoryCertificadoDAO;
import org.example.application.repository.inmemory.inMemoryEventoDAO;
import org.example.application.repository.inmemory.inMemoryParticipanteDAO;
import org.example.application.repository.sqlite.DatabaseBuilder;
import org.example.application.repository.sqlite.SqliteAdministradorDAO;
import org.example.application.view.WindowLoader;
import org.example.domain.usecases.administrador.*;
import org.example.domain.usecases.certificado.*;
import org.example.domain.usecases.evento.*;
import org.example.domain.usecases.participante.*;
import org.example.domain.usecases.participanteInEvento.AgroupDataEventAndParticipantUseCase;

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
    public static SendCertificateByEmailUseCase sendCertificateByEmailUseCase;

    public static CreateEventoUseCase createEventoUseCase;
    public static UpdateEventoUseCase updateEventoUseCase;
    public static FindEventoUseCase findEventoUseCase;
    public static RemoveEventoUseCase removeEventoUseCase;

    public static InvalidHashCodeCertificadoUseCase invalidHashCodeCertificadoUseCase;
    public static AgroupDataEventAndParticipantUseCase agroupDataEventAndParticipantUseCase;

    public static CreateParticipanteUseCase createParticipanteUseCase;
    public static UpdateParticipanteUseCase updateParticipanteUseCase;
    public static FindParticipanteUseCase findParticipanteUseCase;
    public static RemoveParticipanteUseCase removeParticipanteUseCase;
    public static AttachParticipantListUseCase attachParticipantListUseCase;

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
        removeEventoUseCase = new RemoveEventoUseCase(eventoDAO);
        agroupDataEventAndParticipantUseCase = new AgroupDataEventAndParticipantUseCase(eventoDAO);

        ParticipanteDAO participanteDAO = new inMemoryParticipanteDAO();
        createParticipanteUseCase = new CreateParticipanteUseCase(participanteDAO);
        updateParticipanteUseCase = new UpdateParticipanteUseCase(participanteDAO);
        findParticipanteUseCase = new FindParticipanteUseCase(participanteDAO);
        removeParticipanteUseCase = new RemoveParticipanteUseCase(participanteDAO);
        attachParticipantListUseCase = new AttachParticipantListUseCase();

        CertificadoDAO certificadoDAO = new inMemoryCertificadoDAO();
        String pathRelatorios = "relatorios/";
        generatePDFCertificadoUseCase = new GeneratePDFCertificadoUseCase(certificadoDAO, pathRelatorios);
        generateHashCodeCertificadoUseCase = new  GenerateHashCodeCertificadoUseCase(certificadoDAO);
        generateCertificadoUseCase = new GenerateCertificadoUseCase(certificadoDAO, findParticipanteUseCase, findEventoUseCase, generatePDFCertificadoUseCase, generateHashCodeCertificadoUseCase);
        invalidHashCodeCertificadoUseCase = new InvalidHashCodeCertificadoUseCase(certificadoDAO, pathRelatorios);
        updateCertificadoUseCase = new UpdateCertificadoUseCase(certificadoDAO);
        findCertificadoUseCase = new FindCertificadoUseCase(certificadoDAO);
        sendCertificateByEmailUseCase = new SendCertificateByEmailUseCase(findCertificadoUseCase);

    }
}
