package com.entendomeudia.loader;

import com.entendomeudia.model.*;
import com.entendomeudia.service.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class RotinaLoader {
    private final RotinaService rotinaService;
    private final UsuarioService usuarioService;

    public RotinaLoader(RotinaService rotinaService, UsuarioService usuarioService) {
        this.rotinaService = rotinaService;
        this.usuarioService = usuarioService;
    }

    public void carregarRotinas(String path) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(path));
        Map<String, String> map = new HashMap<>();
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                rotinaService.incluirRotina(criarRotina(map));
                map.clear();
            } else {
                String[] parts = line.split("=");
                if (parts.length == 2) map.put(parts[0], parts[1]);
            }
        }
        if (!map.isEmpty()) rotinaService.incluirRotina(criarRotina(map));
        br.close();
    }

    private Rotina criarRotina(Map<String, String> map) throws Exception {
        Usuario usuario = usuarioService.buscarPorId(map.get("usuarioId"));
        Date data = new SimpleDateFormat("yyyy-MM-dd").parse(map.get("data"));
        return new Rotina(map.get("id"), data, usuario);
    }
}