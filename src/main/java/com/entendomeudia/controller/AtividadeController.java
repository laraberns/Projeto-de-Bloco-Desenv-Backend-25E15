package com.entendomeudia.controller;

import com.entendomeudia.model.Atividade;
import com.entendomeudia.service.AtividadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/atividades")
public class AtividadeController {

    @Autowired
    private AtividadeService atividadeService;

    @PostMapping
    public ResponseEntity<?> incluir(@RequestBody Atividade atividade) {
        if (atividade.getNome() == null || atividade.getNome().isBlank()) {
            return ResponseEntity.badRequest().body("O nome da atividade é obrigatório.");
        }
        if (atividade.getHorarioInicio() == null || atividade.getHorarioInicio().isBlank()) {
            return ResponseEntity.badRequest().body("O horário de início é obrigatório.");
        }
        if (atividade.getHorarioFim() == null || atividade.getHorarioFim().isBlank()) {
            return ResponseEntity.badRequest().body("O horário de fim é obrigatório.");
        }

        Atividade nova = atividadeService.incluirAtividade(atividade);
        return ResponseEntity.status(HttpStatus.CREATED).body(nova);
    }

    @GetMapping
    public ResponseEntity<Collection<Atividade>> listar() {
        return ResponseEntity.ok(atividadeService.obterLista());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Atividade atividade = atividadeService.buscarPorId(id);
        return atividade != null
                ? ResponseEntity.ok(atividade)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Atividade não encontrada.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Atividade atividadeAtualizada) {
        Atividade existente = atividadeService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Atividade não encontrada.");
        }

        // Validação manual dos campos obrigatórios
        if (atividadeAtualizada.getNome() == null || atividadeAtualizada.getNome().isBlank()) {
            return ResponseEntity.badRequest().body("O nome da atividade é obrigatório.");
        }
        if (atividadeAtualizada.getHorarioInicio() == null || atividadeAtualizada.getHorarioInicio().isBlank()) {
            return ResponseEntity.badRequest().body("O horário de início é obrigatório.");
        }
        if (atividadeAtualizada.getHorarioFim() == null || atividadeAtualizada.getHorarioFim().isBlank()) {
            return ResponseEntity.badRequest().body("O horário de fim é obrigatório.");
        }

        existente.setNome(atividadeAtualizada.getNome());
        existente.setDescricao(atividadeAtualizada.getDescricao());
        existente.setHorarioInicio(atividadeAtualizada.getHorarioInicio());
        existente.setHorarioFim(atividadeAtualizada.getHorarioFim());
        existente.setStatus(atividadeAtualizada.getStatus());

        return ResponseEntity.ok(atividadeService.incluirAtividade(existente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        Atividade existente = atividadeService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Atividade não encontrada para exclusão.");
        }
        atividadeService.removerPorId(id);
        return ResponseEntity.noContent().build();
    }
}
