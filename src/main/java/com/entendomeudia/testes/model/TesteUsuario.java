package com.entendomeudia.testes.model;

import com.entendomeudia.model.*;

import java.io.*;
import java.util.*;

public class TesteUsuario {

    private static final Map<String, Usuario> usuariosMap = new HashMap<>();

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/dados/usuarios.txt"));
        Map<String, String> map = new HashMap<>();
        String line;

        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) {
                Usuario usuario = criarUsuario(map);
                incluirUsuario(usuario);
                map.clear(); // pronto para o próximo usuário
            } else {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    map.put(parts[0], parts[1]);
                }
            }
        }

        // Inclui o último usuário se o arquivo não terminar com linha em branco
        if (!map.isEmpty()) {
            Usuario usuario = criarUsuario(map);
            incluirUsuario(usuario);
        }

        br.close();

        // Exibir todos os usuários armazenados no Map usando forEach
        recuperarUsuarios().forEach(System.out::println);
    }

    private static Usuario criarUsuario(Map<String, String> map) {
        Usuario usuario = new Usuario(map.get("id"), map.get("nome"), map.get("tipo"), map.get("senha"));
        usuario.setEmail(map.get("email"));
        usuario.setTelefone(map.get("telefone"));
        return usuario;
    }

    private static void incluirUsuario(Usuario usuario) {
        usuariosMap.put(usuario.getId(), usuario);
    }

    private static Collection<Usuario> recuperarUsuarios() {
        return usuariosMap.values();
    }
}
