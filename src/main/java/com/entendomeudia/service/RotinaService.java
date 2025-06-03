package com.entendomeudia.service;

import com.entendomeudia.model.Rotina;
import com.entendomeudia.repository.RotinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

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

    public Rotina atualizarRotina(Long id, Rotina novaRotina) {
        Optional<Rotina> existente = repository.findById(id);
        if (existente.isPresent()) {
            Rotina rotinaAtual = existente.get();
            // Atualizar campos sem alterar id
            rotinaAtual.setData(novaRotina.getData());
            rotinaAtual.setUsuario(novaRotina.getUsuario());
            // Se quiser atualizar lista de atividades, deve-se tratar separadamente
            return repository.save(rotinaAtual);
        }
        return null;
    }

    public boolean removerRotina(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
