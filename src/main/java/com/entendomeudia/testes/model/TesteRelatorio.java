package com.entendomeudia.testes.model;

import com.entendomeudia.model.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class TesteRelatorio {

    private static final Map<String, Relatorio> relatoriosMap = new HashMap<>();

    public static void main(String[] args) throws Exception {
        Usuario usuario = Usuario.fromFile("src/main/resources/dados/usuarios.txt");

        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/dados/relatorios.txt"));
        Map<String, String> map = new HashMap<>();
        String line;

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                incluirRelatorio(criarRelatorio(map, usuario));
                map.clear();
            } else {
                String[] parts = line.split("=");
                if (parts.length == 2) map.put(parts[0], parts[1]);
            }
        }

        if (!map.isEmpty()) {
            incluirRelatorio(criarRelatorio(map, usuario));
        }

        br.close();

        System.out.println("Relatórios cadastrados:");
        recuperarRelatorios().forEach(System.out::println);
    }

    private static Relatorio criarRelatorio(Map<String, String> map, Usuario usuario) throws Exception {
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

    private static void incluirRelatorio(Relatorio relatorio) {
        relatoriosMap.put(relatorio.getId(), relatorio);
    }

    private static Collection<Relatorio> recuperarRelatorios() {
        return relatoriosMap.values();
    }
}
