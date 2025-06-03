package com.entendomeudia.controller;

import com.entendomeudia.model.Relatorio;
import com.entendomeudia.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @PostMapping
    public ResponseEntity<?> incluirRelatorio(@RequestBody Relatorio relatorio) {
        // Validações manuais
        if (relatorio.getUsuario() == null || relatorio.getUsuario().getId() == null) {
            return ResponseEntity.badRequest().body("O usuário do relatório é obrigatório.");
        }

        if (relatorio.getPeriodoInicio() == null || relatorio.getPeriodoFim() == null) {
            return ResponseEntity.badRequest().body("Período inicial e final são obrigatórios.");
        }

        if (relatorio.getPeriodoFim().before(relatorio.getPeriodoInicio())) {
            return ResponseEntity.badRequest().body("O período final não pode ser anterior ao inicial.");
        }

        Relatorio salvo = relatorioService.incluirRelatorio(relatorio);
        return ResponseEntity.status(201).body(salvo);
    }

    @GetMapping
    public ResponseEntity<Collection<Relatorio>> listarRelatorios() {
        Collection<Relatorio> relatorios = relatorioService.recuperarRelatorios();
        return ResponseEntity.ok(relatorios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Relatorio relatorio = relatorioService.buscarPorId(id);
        if (relatorio == null) {
            return ResponseEntity.status(404).body("Relatório não encontrado.");
        }
        return ResponseEntity.ok(relatorio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarRelatorio(@PathVariable Long id, @RequestBody Relatorio novaVersao) {
        // Validações manuais
        if (novaVersao.getUsuario() == null || novaVersao.getUsuario().getId() == null) {
            return ResponseEntity.badRequest().body("O usuário do relatório é obrigatório.");
        }

        if (novaVersao.getPeriodoInicio() == null || novaVersao.getPeriodoFim() == null) {
            return ResponseEntity.badRequest().body("Período inicial e final são obrigatórios.");
        }

        if (novaVersao.getPeriodoFim().before(novaVersao.getPeriodoInicio())) {
            return ResponseEntity.badRequest().body("O período final não pode ser anterior ao inicial.");
        }

        Relatorio atualizado = relatorioService.atualizarRelatorio(id, novaVersao);
        if (atualizado == null) {
            return ResponseEntity.status(404).body("Relatório não encontrado para atualização.");
        }
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerRelatorio(@PathVariable Long id) {
        boolean removido = relatorioService.removerRelatorio(id);
        if (removido) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(404).body("Relatório não encontrado para exclusão.");
    }
}
