package org.example.application.repository.sqlite;

import org.example.domain.entities.certificado.Certificado;
import org.example.domain.usecases.certificado.CertificadoDAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SqliteCertificadoDAO implements CertificadoDAO {
    @Override
    public String create(Certificado type) {
    return null;
    }

    @Override
    public Optional<Certificado> findOne(String key) {
        return null;
    }

    @Override
    public List<Certificado> findAll() {
        return null;
    }

    @Override
    public boolean update(Certificado type) {
        return false;
    }

    @Override
    public boolean deleteByKey(String key) {
        return false;
    }

    @Override
    public boolean delete(Certificado type) {
        return false;
    }
}
