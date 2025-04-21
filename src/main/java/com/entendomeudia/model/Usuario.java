package com.entendomeudia.model;

import java.io.*;
import java.util.*;

public class Usuario {
    private String id;
    private String nome;
    private String tipo; // "responsavel" ou "principal"
    private String email;
    private String telefone;
    private String senha;

    public Usuario(String id, String nome, String tipo, String senha) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.senha = senha;
    }

    public static Usuario fromFile(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        Map<String, String> map = new HashMap<>();
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("=");
            if (parts.length == 2) map.put(parts[0], parts[1]);
        }
        br.close();
        Usuario u = new Usuario(map.get("id"), map.get("nome"), map.get("tipo"), map.get("senha"));
        u.setEmail(map.get("email"));
        u.setTelefone(map.get("telefone"));
        return u;
    }

    public static Usuario fromMap(Map<String, String> map) {
        Usuario u = new Usuario(map.get("id"), map.get("nome"), map.get("tipo"), map.get("senha"));
        u.setEmail(map.get("email"));
        u.setTelefone(map.get("telefone"));
        return u;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Usuario {id='" + id + "', nome='" + nome + "', tipo='" + tipo + "', email='" + email + "', telefone='" + telefone + "'}";
    }
}