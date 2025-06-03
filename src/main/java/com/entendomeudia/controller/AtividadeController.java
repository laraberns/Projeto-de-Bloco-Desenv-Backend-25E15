package com.entendomeudia.controller;

import com.entendomeudia.model.Atividade;
import com.entendomeudia.service.AtividadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/atividades")
public class AtividadeController {

    @Autowired
    private AtividadeService atividadeService;

    @PostMapping
    public Atividade incluir(@RequestBody Atividade atividade) {
        return atividadeService.incluirAtividade(atividade);
    }

    @GetMapping
    public Collection<Atividade> listar() {
        return atividadeService.obterLista();
    }

    @GetMapping("/{id}")
    public Atividade buscarPorId(@PathVariable Long id) {
        return atividadeService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Atividade atualizar(@PathVariable Long id, @RequestBody Atividade atividadeAtualizada) {
        Atividade existente = atividadeService.buscarPorId(id);
        if (existente != null) {
            existente.setNome(atividadeAtualizada.getNome());
            existente.setHorarioInicio(atividadeAtualizada.getHorarioInicio());
            existente.setHorarioFim(atividadeAtualizada.getHorarioFim());
            return atividadeService.incluirAtividade(existente);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        atividadeService.removerPorId(id);
    }
}
