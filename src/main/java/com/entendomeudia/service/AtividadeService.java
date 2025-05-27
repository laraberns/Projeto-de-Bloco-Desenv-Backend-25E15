package com.entendomeudia.service;

import com.entendomeudia.model.Atividade;
import com.entendomeudia.repository.AtividadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class AtividadeService {

    @Autowired
    private AtividadeRepository atividadeRepository;

    public Atividade incluirAtividade(Atividade atividade) {
        return atividadeRepository.save(atividade);
    }

    public Collection<Atividade> obterLista() {
        return atividadeRepository.findAll();
    }

    public Atividade buscarPorId(Long id) {
        Optional<Atividade> resultado = atividadeRepository.findById(id);
        return resultado.orElse(null);
    }
}
