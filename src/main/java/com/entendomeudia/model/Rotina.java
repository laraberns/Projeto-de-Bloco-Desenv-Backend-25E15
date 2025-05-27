package com.entendomeudia.model;

import jakarta.persistence.*; // ou javax.persistence conforme seu projeto
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
@Table(name = "Rotinas")
public class Rotina implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date data;

    // Muitos para 1 com Usuario (muitos rotinas para um usuario)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // 1:N com Atividade - lado "1"
    @OneToMany(mappedBy = "rotina", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Atividade> atividades = new ArrayList<>();

    public Rotina() {
        // construtor padrão para JPA
    }

    public Rotina(String id, Date data, Usuario usuario) {
        // id será gerado, então não deveria setar manualmente
        this.data = data;
        this.usuario = usuario;
    }

    // Factory method continua igual (não é relacionado a JPA)
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

        // Aqui id era String, mas agora id é Long e gerado automaticamente, então ignorar map.get("id")
        return new Rotina(data, usuario);
    }

    // Novo construtor para uso interno
    public Rotina(Date data, Usuario usuario) {
        this.data = data;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Atividade> getAtividades() {
        return atividades;
    }

    public void adicionarAtividade(Atividade atividade) {
        atividades.add(atividade);
        atividade.setRotina(this);  // mantem o relacionamento bidirecional coerente
    }

    public void removerAtividade(Atividade atividade) {
        atividades.remove(atividade);
        atividade.setRotina(null);
    }

    @Override
    public String toString() {
        return "Rotina {id=" + id +
                ", data=" + data +
                ", usuario=" + (usuario != null ? usuario.getNome() : "null") +
                ", atividades=" + atividades.size() +
                '}';
    }
}
