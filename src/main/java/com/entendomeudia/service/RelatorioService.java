package com.entendomeudia.service;

import com.entendomeudia.model.Relatorio;
import java.util.*;

public class RelatorioService {
    private final Map<String, Relatorio> relatorios = new HashMap<>();

    public void incluirRelatorio(Relatorio relatorio) {
        relatorios.put(relatorio.getId(), relatorio);
    }

    public Collection<Relatorio> recuperarRelatorios() {
        return relatorios.values();
    }

    public Relatorio buscarPorId(String id) {
        return relatorios.get(id);
    }
}