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
    public ResponseEntity<?> incluirRotina(@RequestBody Rotina rotina) {
        // Validação: data não pode ser nula
        if (rotina.getData() == null) {
            return ResponseEntity.badRequest().body("A data da rotina é obrigatória.");
        }

        // Opcional: validar usuário (se necessário)
        if (rotina.getUsuario() == null) {
            return ResponseEntity.badRequest().body("O usuário da rotina é obrigatório.");
        }

        Rotina salvo = rotinaService.incluirRotina(rotina);
        return ResponseEntity.status(201).body(salvo);
    }

    @GetMapping
    public ResponseEntity<Collection<Rotina>> listarRotinas() {
        Collection<Rotina> rotinas = rotinaService.recuperarRotinas();
        return ResponseEntity.ok(rotinas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Rotina rotina = rotinaService.buscarPorId(id);
        if (rotina == null) {
            return ResponseEntity.status(404).body("Rotina não encontrada.");
        }
        return ResponseEntity.ok(rotina);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarRotina(@PathVariable Long id, @RequestBody Rotina novaRotina) {
        if (novaRotina.getData() == null) {
            return ResponseEntity.badRequest().body("A data da rotina é obrigatória.");
        }
        if (novaRotina.getUsuario() == null) {
            return ResponseEntity.badRequest().body("O usuário da rotina é obrigatório.");
        }

        Rotina atualizada = rotinaService.atualizarRotina(id, novaRotina);
        if (atualizada == null) {
            return ResponseEntity.status(404).body("Rotina não encontrada para atualização.");
        }
        return ResponseEntity.ok(atualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerRotina(@PathVariable Long id) {
        boolean removido = rotinaService.removerRotina(id);
        if (removido) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(404).body("Rotina não encontrada para exclusão.");
    }
}
