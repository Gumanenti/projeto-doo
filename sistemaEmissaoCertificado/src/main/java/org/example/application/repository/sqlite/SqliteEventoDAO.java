package org.example.application.repository.sqlite;

import org.example.domain.entities.evento.Evento;
import org.example.domain.usecases.evento.EventoDAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class SqliteEventoDAO implements EventoDAO {
    @Override
    public Optional<Evento> findByNome(String nome) {
        return null;
    }

    @Override
    public Integer create(Evento evento) {
        String sql = "INSERT INTO Evento(name, date, hour, speaker, urlImage) VALUES (?,?,?,?,?)";

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setString(1, evento.getNome());
            stmt.setTimestamp(2, Timestamp.valueOf(evento.getData()));
            stmt.setInt(3, evento.getCargaHoraria());
            stmt.setString(4, evento.getNomePalestrante());
            stmt.setString(5, evento.getPathTemplateImage());
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Optional<Evento> findOne(Integer key) {
        return Optional.empty();
    }

    @Override
    public List<Evento> findAll() {
        return null;
    }

    @Override
    public boolean update(Evento evento) {
        return false;
    }

    @Override
    public boolean deleteByKey(Integer key) {
        return false;
    }

    @Override
    public boolean delete(Evento evento) {
        return false;
    }
}
