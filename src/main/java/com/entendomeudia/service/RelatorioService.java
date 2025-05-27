package com.entendomeudia.service;

import com.entendomeudia.model.Relatorio;
import com.entendomeudia.repository.RelatorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RelatorioService {

    @Autowired
    private RelatorioRepository repository;

    public Relatorio incluirRelatorio(Relatorio relatorio) {
        return repository.save(relatorio);
    }

    public Collection<Relatorio> recuperarRelatorios() {
        return repository.findAll();
    }

    public Relatorio buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }
}
