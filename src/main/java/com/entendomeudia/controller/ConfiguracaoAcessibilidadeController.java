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
    public ResponseEntity<?> incluirConfiguracao(@RequestBody ConfiguracaoAcessibilidade config) {
        String erro = validar(config);
        if (erro != null) {
            return ResponseEntity.badRequest().body(erro);
        }
        ConfiguracaoAcessibilidade salvo = service.incluirConfiguracao(config);
        return ResponseEntity.status(201).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarConfiguracao(@PathVariable Long id,
                                                   @RequestBody ConfiguracaoAcessibilidade novaConfig) {
        String erro = validar(novaConfig);
        if (erro != null) {
            return ResponseEntity.badRequest().body(erro);
        }

        ConfiguracaoAcessibilidade atualizada = service.atualizarConfiguracao(id, novaConfig);
        if (atualizada == null) {
            return ResponseEntity.status(404).body("Configuração não encontrada para atualização.");
        }
        return ResponseEntity.ok(atualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerConfiguracao(@PathVariable Long id) {
        boolean existe = service.recuperarConfiguracoes().stream().anyMatch(c -> c.getId().equals(id));
        if (!existe) {
            return ResponseEntity.status(404).body("Configuração não encontrada para exclusão.");
        }
        service.removerPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Collection<ConfiguracaoAcessibilidade>> listarConfiguracoes() {
        return ResponseEntity.ok(service.recuperarConfiguracoes());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> buscarPorUsuarioId(@PathVariable Long usuarioId) {
        ConfiguracaoAcessibilidade config = service.buscarPorUsuarioId(usuarioId);
        if (config == null) {
            return ResponseEntity.status(404).body("Configuração não encontrada para este usuário.");
        }
        return ResponseEntity.ok(config);
    }

    // Validação apenas do campo String necessário
    private String validar(ConfiguracaoAcessibilidade config) {
        if (config.getTamanhoFonte() == null || config.getTamanhoFonte().isBlank()) {
            return "O tamanho da fonte é obrigatório (pequeno, medio ou grande).";
        }
        return null;
    }
}
