package com.entendomeudia.loader;

import com.entendomeudia.model.*;
import com.entendomeudia.service.*;

import java.io.*;
import java.util.*;

public class UsuarioLoader {
    private final UsuarioService usuarioService;

    public UsuarioLoader(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public void carregarUsuarios(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        Map<String, String> map = new HashMap<>();
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                usuarioService.incluirUsuario(criarUsuario(map));
                map.clear();
            } else {
                String[] parts = line.split("=");
                if (parts.length == 2) map.put(parts[0], parts[1]);
            }
        }
        if (!map.isEmpty()) usuarioService.incluirUsuario(criarUsuario(map));
        br.close();
    }

    private Usuario criarUsuario(Map<String, String> map) {
        Usuario u = new Usuario(map.get("id"), map.get("nome"), map.get("tipo"), map.get("senha"));
        u.setEmail(map.get("email"));
        u.setTelefone(map.get("telefone"));
        return u;
    }
}