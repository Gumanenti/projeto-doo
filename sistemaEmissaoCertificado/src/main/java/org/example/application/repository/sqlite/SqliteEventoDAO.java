package org.example.application.repository.sqlite;

import org.example.domain.entities.evento.Evento;
import org.example.domain.usecases.evento.EventoDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SqliteEventoDAO implements EventoDAO {
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    @Override
    public Optional<Evento> findByNome(String nome) {
        String sql = "SELECT * FROM Evento WHERE name = ?";
        Evento evento = null;

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                evento = resultSetToEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(evento);
    }

    @Override
    public Integer create(Evento evento) {
        String sql = "INSERT INTO Evento(name, dateTime, workTime, speaker, urlImage) VALUES (?,?,?,?,?)";

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setString(1, evento.getNome());
            stmt.setString(2, evento.getData().format(formatter));
            stmt.setInt(3, evento.getCargaHoraria());
            stmt.setString(4, evento.getNomePalestrante());
            stmt.setString(5, "evento.getPathTemplateImage()");
            stmt.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Optional<Evento> findOne(Integer key) {
        String sql = "SELECT * FROM Evento WHERE id = ?";
        Evento evento = null;

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                evento = resultSetToEntity(rs);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return Optional.ofNullable(evento);
    }

    @Override
    public List<Evento> findAll() {
        String sql = "SELECT * from Evento";
        List<Evento> eventoList = new ArrayList<>();

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Evento evento = resultSetToEntity(rs);
                eventoList.add(evento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eventoList;
    }

    @Override
    public boolean update(Evento evento) {
        String sql = "UPDATE Evento SET name = ?, dateTime = ?, workTime = ?, speaker = ?, urlImage = ? WHERE id = ?  ";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setString(1, evento.getNome());
            stmt.setString(2, evento.getData().format(formatter));
            stmt.setInt(3, evento.getCargaHoraria());
            stmt.setString(4, evento.getNomePalestrante());
            stmt.setString(5, evento.getPathTemplateImage());
            stmt.setInt(6, evento.getId());
            stmt.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteByKey(Integer key) {
        String sql = "DELETE FROM Evento WHERE id = ?";

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setInt(1, key);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(Evento evento) {
        return deleteByKey(evento.getId());
    }

    private Evento resultSetToEntity(ResultSet rs) throws SQLException {
        return new Evento(
                rs.getInt("id"),
                rs.getString("name"),
                LocalDateTime.parse(rs.getString("dateTime"), formatter),
                rs.getInt("workTime"),
                rs.getString("speaker"),
                rs.getString("urlImage")
        );
    }

}
