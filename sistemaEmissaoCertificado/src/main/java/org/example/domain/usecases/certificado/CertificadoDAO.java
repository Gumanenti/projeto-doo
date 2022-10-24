package org.example.domain.usecases.certificado;

import org.example.domain.entities.certificado.Certificado;
import org.example.domain.usecases.utils.DAO;

import java.util.Optional;

public interface CertificadoDAO extends DAO<Certificado, String> {
    Optional<Certificado> findByCodigo(String codigo);
}
