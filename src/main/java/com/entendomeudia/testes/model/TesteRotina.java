package com.entendomeudia.testes.model;

import com.entendomeudia.model.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class TesteRotina {

    private static final Map<String, Rotina> rotinasMap = new HashMap<>();

    public static void main(String[] args) throws Exception {
        Usuario usuario = Usuario.fromFile("src/main/resources/dados/usuarios.txt");

        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/dados/rotinas.txt"));
        Map<String, String> dados = new HashMap<>();
        String line;

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                incluirRotina(criarRotina(dados, usuario));
                dados.clear();
            } else {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    dados.put(parts[0], parts[1]);
                }
            }
        }

        // Adiciona a última rotina se o arquivo não terminar com linha em branco
        if (!dados.isEmpty()) {
            incluirRotina(criarRotina(dados, usuario));
        }

        br.close();

        System.out.println("Rotinas cadastradas:");
        recuperarRotinas().forEach(System.out::println);
    }

    private static Rotina criarRotina(Map<String, String> dados, Usuario usuario) throws Exception {
        String id = dados.get("id");
        Date data = new SimpleDateFormat("yyyy-MM-dd").parse(dados.get("data"));
        return new Rotina(id, data, usuario);
    }

    private static void incluirRotina(Rotina rotina) {
        rotinasMap.put(rotina.getId(), rotina);
    }

    private static Collection<Rotina> recuperarRotinas() {
        return rotinasMap.values();
    }
}
