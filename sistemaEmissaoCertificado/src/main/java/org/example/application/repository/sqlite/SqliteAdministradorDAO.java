package org.example.application.repository.sqlite;

import org.example.domain.entities.administrador.Administrador;
import org.example.domain.usecases.administrador.AdministradorDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SqliteAdministradorDAO implements AdministradorDAO {

    @Override
    public String create(Administrador administrador) {
        String sql = "INSERT INTO Administrador(login, password, keyWord) VALUES (?,?,?)";

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setString(1, administrador.getLogin());
            stmt.setString(2, administrador.getSenha());
            stmt.setString(3, administrador.getPalavraChave());

            stmt.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Administrador> findOne(String key) {
        String sql = "SELECT * FROM Administrador WHERE login like \"?\"";
        Administrador administrador = null;

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setString(1, key);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                administrador = resultSetToEntity(rs);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return Optional.ofNullable(administrador);
    }

    @Override
    public List<Administrador> findAll() {
        String sql = "SELECT * FROM Administrador";
        List<Administrador> administradores = new ArrayList<>();

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Administrador administrador = resultSetToEntity(rs);
                administradores.add(administrador);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return administradores;
    }

    @Override
    public boolean update(Administrador administrador) {
        String sql = "UPDATE Administrador SET password = ?, keyWord = ? WHERE login = ?";

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){

            stmt.setString(1, administrador.getSenha());
            stmt.setString(2, administrador.getPalavraChave());
            stmt.setString(3, administrador.getLogin());

            stmt.execute();

            return true;

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteByKey(String key) {
        String sql = "DELETE FROM Administrador WHERE id = ?";
        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setString(1, key);
            stmt.execute();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Administrador administrador) {
        return deleteByKey(administrador.getLogin());
    }

    private Administrador resultSetToEntity(ResultSet rs) throws SQLException {
        return new Administrador(
                rs.getString("login"),
                rs.getString("password"),
                rs.getString("keyWord")
        );
    }
}
