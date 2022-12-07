package org.example.application.repository.sqlite;

import org.example.domain.entities.participante.Participante;
import org.example.domain.usecases.participante.ParticipanteDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SqlitteParticipanteDAO implements ParticipanteDAO {
    @Override
    public String create(Participante participante) {
        String sql = "INSERT INTO Participante (cpf, name, email) VALUES (?,?,?)";

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setString(1, participante.getCpf());
            stmt.setString(2, participante.getNome());
            stmt.setString(3, participante.getEmail());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Optional<Participante> findOne(String key) {
        String sql = "SELECT * FROM Participante WHERE cpf = ?";
        Participante participante = null;

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setString(1, key);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                participante = resultSetToEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(participante);
    }

    @Override
    public List<Participante> findAll() {
        String sql = "SELECT * FROM Participante";
        List<Participante> participanteList = new ArrayList<>();

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Participante participante = resultSetToEntity(rs);
                participanteList.add(participante);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return participanteList;
    }

    @Override
    public boolean update(Participante participante) {
        String sql = "UPDATE Participante SET name = ?, email = ? WHERE cpf = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setString(1, participante.getNome());
            stmt.setString(2, participante.getEmail());
            stmt.setString(3, participante.getCpf());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteByKey(String key) {
        String sql = "DELETE FROM Participante WHERE cpf = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setString(1, key);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Participante participante) {
        return deleteByKey(participante.getCpf());
    }

    private Participante resultSetToEntity(ResultSet rs) throws SQLException {
        return new Participante(
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("cpf")
        );
    }
}
