package com.entendomeudia.controller;

import com.entendomeudia.model.ConfiguracaoAcessibilidade;
import com.entendomeudia.service.ConfiguracaoAcessibilidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/configuracoes")
public class ConfiguracaoAcessibilidadeController {

    @Autowired
    private ConfiguracaoAcessibilidadeService service;

    @PostMapping
    public ResponseEntity<ConfiguracaoAcessibilidade> incluirConfiguracao(@RequestBody ConfiguracaoAcessibilidade config) {
        ConfiguracaoAcessibilidade salvo = service.incluirConfiguracao(config);
        return ResponseEntity.ok(salvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerConfiguracao(@PathVariable Long id) {
        boolean existe = service.recuperarConfiguracoes().stream().anyMatch(c -> c.getId().equals(id));
        if (existe) {
            service.removerPorId(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConfiguracaoAcessibilidade> atualizarConfiguracao(
            @PathVariable Long id,
            @RequestBody ConfiguracaoAcessibilidade novaConfig) {

        ConfiguracaoAcessibilidade atualizada = service.atualizarConfiguracao(id, novaConfig);
        if (atualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atualizada);
    }

    @GetMapping
    public ResponseEntity<Collection<ConfiguracaoAcessibilidade>> listarConfiguracoes() {
        Collection<ConfiguracaoAcessibilidade> lista = service.recuperarConfiguracoes();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<ConfiguracaoAcessibilidade> buscarPorUsuarioId(@PathVariable Long usuarioId) {
        ConfiguracaoAcessibilidade config = service.buscarPorUsuarioId(usuarioId);
        if (config == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(config);
    }
}
