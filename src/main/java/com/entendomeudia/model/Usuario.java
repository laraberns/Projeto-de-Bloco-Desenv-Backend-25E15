package com.entendomeudia.model;

import jakarta.persistence.*;

import java.io.*;
import java.util.*;

@Entity
@Table(name = "Usuarios")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String tipo; // "responsavel" ou "principal"

    private String email;

    private String telefone;

    private String senha;

    // Exemplo de mapeamento 1:N com Rotina (opcional, para relacionamento bidirecional)
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rotina> rotinas = new ArrayList<>();

    // Exemplo de mapeamento 1:1 com ConfiguracaoAcessibilidade (opcional)
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ConfiguracaoAcessibilidade configuracaoAcessibilidade;

    // Exemplo de mapeamento 1:1 com Relatorio (opcional)
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Relatorio relatorio;

    public Usuario() {
        // Construtor padrão para JPA
    }

    public Usuario(String nome, String tipo, String senha) {
        this.nome = nome;
        this.tipo = tipo;
        this.senha = senha;
    }

    // Os métodos fromFile e fromMap precisam ser ajustados para o novo tipo do id.
    // Como o id será gerado automaticamente, ignoramos o valor vindo do arquivo.

    public static Usuario fromFile(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        Map<String, String> map = new HashMap<>();
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("=");
            if (parts.length == 2) map.put(parts[0], parts[1]);
        }
        br.close();

        Usuario u = new Usuario(map.get("nome"), map.get("tipo"), map.get("senha"));
        u.setEmail(map.get("email"));
        u.setTelefone(map.get("telefone"));
        return u;
    }

    public static Usuario fromMap(Map<String, String> map) {
        Usuario u = new Usuario(map.get("nome"), map.get("tipo"), map.get("senha"));
        u.setEmail(map.get("email"));
        u.setTelefone(map.get("telefone"));
        return u;
    }

    // Getters e setters

    public Long getId() {
        return id;
    }

    // Sem setter para id (gerado automaticamente)

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Rotina> getRotinas() {
        return rotinas;
    }

    public void setRotinas(List<Rotina> rotinas) {
        this.rotinas = rotinas;
    }

    public ConfiguracaoAcessibilidade getConfiguracaoAcessibilidade() {
        return configuracaoAcessibilidade;
    }

    public void setConfiguracaoAcessibilidade(ConfiguracaoAcessibilidade configuracaoAcessibilidade) {
        this.configuracaoAcessibilidade = configuracaoAcessibilidade;
    }

    public Relatorio getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(Relatorio relatorio) {
        this.relatorio = relatorio;
    }

    @Override
    public String toString() {
        return "Usuario {id=" + id + ", nome='" + nome + "', tipo='" + tipo + "', email='" + email + "', telefone='" + telefone + "'}";
    }
}
