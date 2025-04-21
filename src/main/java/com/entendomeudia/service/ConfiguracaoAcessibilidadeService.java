package com.entendomeudia.service;

import com.entendomeudia.model.ConfiguracaoAcessibilidade;
import java.util.*;

public class ConfiguracaoAcessibilidadeService {
    private final Map<String, ConfiguracaoAcessibilidade> configuracoes = new HashMap<>();

    public void incluirConfiguracao(ConfiguracaoAcessibilidade config) {
        configuracoes.put(config.getUsuario().getId(), config);
    }

    public Collection<ConfiguracaoAcessibilidade> recuperarConfiguracoes() {
        return configuracoes.values();
    }

    public ConfiguracaoAcessibilidade buscarPorUsuarioId(String id) {
        return configuracoes.get(id);
    }
}