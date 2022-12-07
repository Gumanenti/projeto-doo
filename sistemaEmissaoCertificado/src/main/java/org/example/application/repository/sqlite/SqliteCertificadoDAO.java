package org.example.application.repository.sqlite;

import org.example.domain.entities.certificado.Certificado;
import org.example.domain.entities.certificado.CertificadoStatus;
import org.example.domain.entities.evento.Evento;
import org.example.domain.entities.participante.Participante;
import org.example.domain.usecases.certificado.CertificadoDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.application.main.Main.findEventoUseCase;
import static org.example.application.main.Main.findParticipanteUseCase;

public class SqliteCertificadoDAO implements CertificadoDAO {
    @Override
    public String create(Certificado certificado) {
        String sql = "INSERT INTO Certificado (hashCode, eventId, participantCpf, status) VALUES (?,?,?,?)";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setString(1, certificado.getCodigo());
            stmt.setInt(2, certificado.getEvento().getId());
            stmt.setString(3, certificado.getParticipante().getCpf());
            stmt.setString(4, converterStatusToDB(certificado.getCertificadoStatus()));

            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Optional<Certificado> findOne(String key) {
        String sql = "SELECT * FROM Certificado WHERE hashCode like ?";
        Certificado certificado = null;

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){

            String builder = "%" +
                    key +
                    "%";

            stmt.setString(1, builder);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                certificado = resultSetToEntity(rs);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return Optional.ofNullable(certificado);
    }

    @Override
    public List<Certificado> findAll() {
        String sql = "SELECT * FROM Certificado";
        List<Certificado> certificadoList = new ArrayList<>();

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Certificado certificado = resultSetToEntity(rs);
                certificadoList.add(certificado);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return certificadoList;
    }

    @Override
    public boolean update(Certificado certificado) {
        String sql = "UPDATE Certificado SET eventId = ?, participantCpf = ?, status = ? WHERE hashCode = ?";

        try (PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setInt(1, certificado.getEvento().getId());
            stmt.setString(2, certificado.getParticipante().getCpf());
            stmt.setString(3, converterStatusToDB(certificado.getCertificadoStatus()));
            stmt.setString(4, certificado.getCodigo());
            stmt.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteByKey(String key) {
        String sql = "DELETE FROM Certificado WHERE hashCode = ?";

        try(PreparedStatement stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setString(1, key);
            stmt.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(Certificado certificado) {
        return  deleteByKey(certificado.getCodigo());
    }

    private Certificado resultSetToEntity(ResultSet rs) throws SQLException {
        Evento evento = findEventoUseCase.findOne(rs.getInt("eventId")).get();
        Participante participante = findParticipanteUseCase.findOne(rs.getString("participantCpf")).get();

        return new Certificado(
                evento,
                participante,
                rs.getString("hashCode"),
                converterDBToStatus(rs.getString("status"))
        );
    }

    private CertificadoStatus converterDBToStatus(String status) {
        switch (status){
            case "valid":
                return CertificadoStatus.VALID;

            case "invalid":
                return CertificadoStatus.INVALID;
        }
        return null;
    }

    private String converterStatusToDB(CertificadoStatus status){
        switch (status){
            case VALID:
                return "valid";

            case INVALID:
                return "invalid";
        }
        return null;
    }
}
