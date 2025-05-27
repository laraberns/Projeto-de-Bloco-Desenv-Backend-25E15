package com.entendomeudia.service;

import com.entendomeudia.model.Rotina;
import com.entendomeudia.repository.RotinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RotinaService {

    @Autowired
    private RotinaRepository repository;

    public Rotina incluirRotina(Rotina rotina) {
        return repository.save(rotina);
    }

    public Collection<Rotina> recuperarRotinas() {
        return repository.findAll();
    }

    public Rotina buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }
}
