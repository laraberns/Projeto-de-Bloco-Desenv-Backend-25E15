package com.entendomeudia.controller;

import com.entendomeudia.model.Relatorio;
import com.entendomeudia.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @PostMapping
    public ResponseEntity<Relatorio> incluirRelatorio(@RequestBody Relatorio relatorio) {
        Relatorio salvo = relatorioService.incluirRelatorio(relatorio);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping
    public ResponseEntity<Collection<Relatorio>> listarRelatorios() {
        Collection<Relatorio> relatorios = relatorioService.recuperarRelatorios();
        return ResponseEntity.ok(relatorios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Relatorio> buscarPorId(@PathVariable Long id) {
        Relatorio relatorio = relatorioService.buscarPorId(id);
        if (relatorio == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(relatorio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Relatorio> atualizarRelatorio(@PathVariable Long id, @RequestBody Relatorio novaVersao) {
        Relatorio atualizado = relatorioService.atualizarRelatorio(id, novaVersao);
        if (atualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerRelatorio(@PathVariable Long id) {
        boolean removido = relatorioService.removerRelatorio(id);
        if (removido) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
