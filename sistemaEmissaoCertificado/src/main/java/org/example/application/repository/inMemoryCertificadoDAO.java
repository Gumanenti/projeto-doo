package org.example.application.repository;

import org.example.domain.entities.administrador.Administrador;
import org.example.domain.entities.certificado.Certificado;
import org.example.domain.usecases.administrador.AdministradorDAO;
import org.example.domain.usecases.certificado.CertificadoDAO;

import java.util.*;

public class inMemoryCertificadoDAO implements CertificadoDAO {

    private static final Map<String, Certificado> db = new LinkedHashMap<>();

    @Override
    public Optional<Certificado> findByCodigo(String codigo) {
        return db.values().stream()
                .filter(certificado -> certificado.getCodigo().equals(codigo))
                .findAny();
    }

    @Override
    public String create(Certificado certificado) {
        db.put(certificado.getCodigo(), certificado);
        return certificado.getCodigo();
    }

    @Override
    public Optional<Certificado> findOne(String key) {
        if(db.containsKey(key)){
            return Optional.of(db.get(key));
        }
        return Optional.empty();
    }

    @Override
    public List<Certificado> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public boolean update(Certificado certificado) {
        String codigo = certificado.getCodigo();
        if(db.containsKey(codigo)){
            db.replace(codigo, certificado);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteByKey(String key) {
        if(db.containsKey(key)){
            db.remove(key);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Certificado certificado) {
        return deleteByKey(certificado.getCodigo());
    }
}
