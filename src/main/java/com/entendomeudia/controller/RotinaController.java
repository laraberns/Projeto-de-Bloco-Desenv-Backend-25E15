package com.entendomeudia.controller;

import com.entendomeudia.model.Rotina;
import com.entendomeudia.service.RotinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/rotinas")
public class RotinaController {

    @Autowired
    private RotinaService rotinaService;

    @PostMapping
    public ResponseEntity<Rotina> incluirRotina(@RequestBody Rotina rotina) {
        Rotina salvo = rotinaService.incluirRotina(rotina);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping
    public ResponseEntity<Collection<Rotina>> listarRotinas() {
        Collection<Rotina> rotinas = rotinaService.recuperarRotinas();
        return ResponseEntity.ok(rotinas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rotina> buscarPorId(@PathVariable Long id) {
        Rotina rotina = rotinaService.buscarPorId(id);
        if (rotina == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rotina);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rotina> atualizarRotina(@PathVariable Long id, @RequestBody Rotina novaRotina) {
        Rotina atualizada = rotinaService.atualizarRotina(id, novaRotina);
        if (atualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerRotina(@PathVariable Long id) {
        boolean removido = rotinaService.removerRotina(id);
        if (removido) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
