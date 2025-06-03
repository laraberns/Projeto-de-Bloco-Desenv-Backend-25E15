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

    public Relatorio atualizarRelatorio(Long id, Relatorio novaVersao) {
        return repository.findById(id).map(relatorioExistente -> {
            // Atualiza campos (sem alterar id)
            relatorioExistente.setUsuario(novaVersao.getUsuario());
            relatorioExistente.setPeriodoInicio(novaVersao.getPeriodoInicio());
            relatorioExistente.setPeriodoFim(novaVersao.getPeriodoFim());
            relatorioExistente.setTotalAtividades(novaVersao.getTotalAtividades());
            relatorioExistente.setConcluidas(novaVersao.getConcluidas());
            relatorioExistente.setNaoRealizadas(novaVersao.getNaoRealizadas());

            return repository.save(relatorioExistente);
        }).orElse(null);
    }

    public boolean removerRelatorio(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
