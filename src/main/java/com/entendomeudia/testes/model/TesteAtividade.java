package com.entendomeudia.testes.model;

import com.entendomeudia.model.*;

import java.io.*;
import java.util.*;

public class TesteAtividade {

    private static final Map<String, Atividade> atividadesMap = new HashMap<>();

    public static void main(String[] args) throws Exception {
        Usuario usuario = Usuario.fromFile("src/main/resources/dados/usuarios.txt");
        Rotina rotina = Rotina.fromFile("src/main/resources/dados/rotinas.txt", usuario);

        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/dados/atividades.txt"));
        Map<String, String> map = new HashMap<>();
        String line;

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                incluirAtividade(criarAtividade(map, rotina));
                map.clear();
            } else {
                String[] parts = line.split("=");
                if (parts.length == 2) map.put(parts[0], parts[1]);
            }
        }
        if (!map.isEmpty()) {
            incluirAtividade(criarAtividade(map, rotina));
        }
        br.close();

        System.out.println("Atividades cadastradas:");
        recuperarAtividades().forEach(System.out::println);
    }

    private static Atividade criarAtividade(Map<String, String> map, Rotina rotina) {
        Atividade a = new Atividade(map.get("id"), map.get("nome"), map.get("horarioInicio"), map.get("horarioFim"));
        a.setDescricao(map.get("descricao"));
        a.setRotina(rotina);
        return a;
    }

    private static void incluirAtividade(Atividade atividade) {
        atividadesMap.put(atividade.getId(), atividade);
    }

    private static Collection<Atividade> recuperarAtividades() {
        return atividadesMap.values();
    }
}
