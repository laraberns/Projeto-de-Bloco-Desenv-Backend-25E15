package com.entendomeudia.loader;

import com.entendomeudia.model.*;
import com.entendomeudia.service.*;

import java.io.*;
import java.util.*;

public class AtividadeLoader {
    private final AtividadeService atividadeService;
    private final RotinaService rotinaService;

    public AtividadeLoader(AtividadeService atividadeService, RotinaService rotinaService) {
        this.atividadeService = atividadeService;
        this.rotinaService = rotinaService;
    }

    public void carregarAtividades(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        Map<String, String> map = new HashMap<>();
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                atividadeService.incluirAtividade(criarAtividade(map));
                map.clear();
            } else {
                String[] parts = line.split("=");
                if (parts.length == 2) map.put(parts[0], parts[1]);
            }
        }
        if (!map.isEmpty()) atividadeService.incluirAtividade(criarAtividade(map));
        br.close();
    }

    private Atividade criarAtividade(Map<String, String> map) {
        Rotina rotina = rotinaService.buscarPorId(map.get("rotinaId"));
        Atividade a = new Atividade(map.get("id"), map.get("nome"), map.get("horarioInicio"), map.get("horarioFim"));
        a.setDescricao(map.get("descricao"));
        a.setRotina(rotina);
        return a;
    }
}