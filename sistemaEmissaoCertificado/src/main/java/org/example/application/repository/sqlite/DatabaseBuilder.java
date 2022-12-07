package org.example.application.repository.sqlite;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseBuilder {

    public void buildDatabaseIfMissing() {
        if (isDatabaseMissing()) {
            System.out.println("Database is missing. Building database: \n");
            buildTables();
        }
    }

    private boolean isDatabaseMissing() {
        return !Files.exists(Paths.get("database.db"));
    }

    private void buildTables() {
        try (Statement statement = ConnectionFactory.createStatement()) {
            statement.addBatch(createTableAdministrador());
            statement.addBatch(createTableEvento());
            statement.addBatch(createTableCertificados());
            statement.executeBatch();

            System.out.println("Database successfully created.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private String createTableAdministrador() {

        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE Administrador (\n")
                .append("login TEXT PRIMARY KEY,  \n")
                .append("password TEXT NOT NULL, \n")
                .append("keyWord TEXT NOT NULL \n")
                .append("); \n");

        return builder.toString();
    }

    private String createTableEvento() {

        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE Evento (\n")
                .append("id INTEGER PRIMARY KEY AUTOINCREMENT, \n")
                .append("name TEXT NOT NULL UNIQUE, \n")
                .append("date DATETIME NOT NULL,\n")
                .append("hour INTEGER NOT NULL, \n") // Não sei um nome correto para cargaHoraria.
                .append("speaker TEXT NOT NULL, \n")
                .append("urlImage TEXT NOT NULL \n")
                .append("); \n");

        return builder.toString();
    }

    private String createTableCertificados() {

        StringBuilder builder = new StringBuilder();

        builder.append("CREATE TABLE Certificado (\n")
                .append("hashCode TEXT PRIMARY KEY, \n")
                .append("eventId INTEGER NOT NULL, \n")
                .append("participantCpf TEXT NOT NULL, \n")
                .append("status TEXT NOT NULL, \n")
                .append("FOREIGN KEY(eventID) references Evento(id) \n")
                .append("); \n");

        return builder.toString();
    }

}
