package com.entendomeudia.service;

import com.entendomeudia.model.ConfiguracaoAcessibilidade;
import com.entendomeudia.repository.ConfiguracaoAcessibilidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

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

    public ConfiguracaoAcessibilidade buscarPorId(Long id) {
        Optional<ConfiguracaoAcessibilidade> resultado = repository.findById(id);
        return resultado.orElse(null);
    }

    public ConfiguracaoAcessibilidade atualizarConfiguracao(Long id, ConfiguracaoAcessibilidade novaConfig) {
        Optional<ConfiguracaoAcessibilidade> existente = repository.findById(id);

        if (existente.isPresent()) {
            ConfiguracaoAcessibilidade configAtual = existente.get();

            // Atualiza apenas os campos que podem ser modificados
            configAtual.setContraste(novaConfig.isContraste());
            configAtual.setLeituraVoz(novaConfig.isLeituraVoz());
            configAtual.setTamanhoFonte(novaConfig.getTamanhoFonte());
            configAtual.setUsuario(novaConfig.getUsuario()); // se puder ser alterado

            return repository.save(configAtual);
        }

        return null;
    }


    public void removerPorId(Long id) {
        repository.deleteById(id);
    }
}
