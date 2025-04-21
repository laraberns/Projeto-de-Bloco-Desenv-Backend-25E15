package com.entendomeudia.service;

import com.entendomeudia.model.Atividade;
import java.util.*;

public class AtividadeService {
    private final Map<String, Atividade> atividades = new HashMap<>();

    public void incluirAtividade(Atividade atividade) {
        atividades.put(atividade.getId(), atividade);
    }

    public Collection<Atividade> obterLista() {
        return atividades.values();
    }

    public Atividade buscarPorId(String id) {
        return atividades.get(id);
    }
}
