package com.entendomeudia.service;

import com.entendomeudia.model.ConfiguracaoAcessibilidade;
import com.entendomeudia.repository.ConfiguracaoAcessibilidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ConfiguracaoAcessibilidadeService {

    @Autowired
    private ConfiguracaoAcessibilidadeRepository repository;

    public ConfiguracaoAcessibilidade incluirConfiguracao(ConfiguracaoAcessibilidade config) {
        return repository.save(config);
    }

    public Collection<ConfiguracaoAcessibilidade> recuperarConfiguracoes() {
        return repository.findAll();
    }

    public ConfiguracaoAcessibilidade buscarPorUsuarioId(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }
}
