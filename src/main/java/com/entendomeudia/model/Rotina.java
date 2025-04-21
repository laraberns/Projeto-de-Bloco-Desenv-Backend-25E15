package com.entendomeudia.model;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Rotina {
    private String id;
    private Date data;
    private Usuario usuario;
    private List<Atividade> atividades = new ArrayList<>(); // 1:N com Atividade

    public Rotina(String id, Date data, Usuario usuario) {
        this.id = id;
        this.data = data;
        this.usuario = usuario;
    }

    public static Rotina fromFile(String path, Usuario usuario) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(path));
        Map<String, String> map = new HashMap<>();
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("=");
            if (parts.length == 2) map.put(parts[0], parts[1]);
        }
        br.close();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date data = sdf.parse(map.get("data"));

        return new Rotina(map.get("id"), data, usuario);
    }

    public String getId() {
        return id;
    }

    public void adicionarAtividade(Atividade atividade) {
        atividades.add(atividade);
    }

    @Override
    public String toString() {
        return "Rotina {id='" + id + "', data=" + data + ", usuario='" + usuario.getNome() + "', atividades=" + atividades.size() + "}";
    }
}