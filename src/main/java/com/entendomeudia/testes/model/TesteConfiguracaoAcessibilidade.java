package com.entendomeudia.testes.model;

import com.entendomeudia.model.*;

import java.io.*;
import java.util.*;

public class TesteConfiguracaoAcessibilidade {

    private static final Map<String, ConfiguracaoAcessibilidade> acessibilidadeMap = new HashMap<>();
    private static final Map<String, Usuario> usuariosMap = new HashMap<>();

    public static void main(String[] args) throws Exception {
        carregarUsuarios("src/main/resources/dados/usuarios.txt");

        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/dados/acessibilidade.txt"));
        Map<String, String> map = new HashMap<>();
        String line;

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                incluirConfiguracao(criarConfiguracao(map));
                map.clear();
            } else {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    map.put(parts[0], parts[1]);
                }
            }
        }

        if (!map.isEmpty()) {
            incluirConfiguracao(criarConfiguracao(map));
        }

        br.close();

        System.out.println("Configurações de Acessibilidade cadastradas:");
        recuperarConfiguracoes().forEach(System.out::println);
    }

    private static void carregarUsuarios(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        Map<String, String> userMap = new HashMap<>();
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                Usuario u = Usuario.fromMap(userMap);
                usuariosMap.put(u.getId(), u);
                userMap.clear();
            } else {
                String[] parts = line.split("=");
                if (parts.length == 2) userMap.put(parts[0], parts[1]);
            }
        }
        if (!userMap.isEmpty()) {
            Usuario u = Usuario.fromMap(userMap);
            usuariosMap.put(u.getId(), u);
        }
        br.close();
    }

    private static ConfiguracaoAcessibilidade criarConfiguracao(Map<String, String> map) {
        Usuario usuario = usuariosMap.get(map.get("usuarioId"));
        ConfiguracaoAcessibilidade config = new ConfiguracaoAcessibilidade(usuario);
        config.setId(map.get("id"));
        config.setTamanhoFonte(map.get("tamanhoFonte"));
        config.setContraste(Boolean.parseBoolean(map.get("contraste")));
        config.setLeituraVoz(Boolean.parseBoolean(map.get("leituraVoz")));
        return config;
    }

    private static void incluirConfiguracao(ConfiguracaoAcessibilidade config) {
        acessibilidadeMap.put(config.getUsuario().getId(), config);
    }

    private static Collection<ConfiguracaoAcessibilidade> recuperarConfiguracoes() {
        return acessibilidadeMap.values();
    }
}
