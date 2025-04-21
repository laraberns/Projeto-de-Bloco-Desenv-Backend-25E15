// Loader de Relatorio
package com.entendomeudia.loader;

import com.entendomeudia.model.*;
import com.entendomeudia.service.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class RelatorioLoader {
    private final RelatorioService relatorioService;
    private final UsuarioService usuarioService;

    public RelatorioLoader(RelatorioService relatorioService, UsuarioService usuarioService) {
        this.relatorioService = relatorioService;
        this.usuarioService = usuarioService;
    }

    public void carregarRelatorios(String path) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(path));
        Map<String, String> map = new HashMap<>();
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                relatorioService.incluirRelatorio(criarRelatorio(map));
                map.clear();
            } else {
                String[] parts = line.split("=");
                if (parts.length == 2) map.put(parts[0], parts[1]);
            }
        }
        if (!map.isEmpty()) relatorioService.incluirRelatorio(criarRelatorio(map));
        br.close();
    }

    private Relatorio criarRelatorio(Map<String, String> map) throws Exception {
        Usuario usuario = usuarioService.buscarPorId(map.get("usuarioId"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date inicio = sdf.parse(map.get("periodoInicio"));
        Date fim = sdf.parse(map.get("periodoFim"));
        Relatorio r = new Relatorio(usuario, inicio, fim);
        r.setId(map.get("id"));
        r.setTotalAtividades(Integer.parseInt(map.get("totalAtividades")));
        r.setConcluidas(Integer.parseInt(map.get("concluidas")));
        r.setNaoRealizadas(Integer.parseInt(map.get("naoRealizadas")));
        return r;
    }
}