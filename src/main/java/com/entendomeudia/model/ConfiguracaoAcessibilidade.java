package com.entendomeudia.model;

import jakarta.persistence.*; // ou javax.persistence conforme seu projeto

import java.io.Serializable;

@Entity
@Table(name = "ConfiguracoesAcessibilidade")
public class ConfiguracaoAcessibilidade implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento 1:1 com Usuario, assumindo que existe classe Usuario anotada
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", unique = true) // FK e restrição de unicidade
    private Usuario usuario;

    private String tamanhoFonte; // pequeno, medio, grande

    private boolean contraste;

    private boolean leituraVoz;

    public ConfiguracaoAcessibilidade() {
        // construtor padrão obrigatório para JPA
    }

    public ConfiguracaoAcessibilidade(Usuario usuario) {
        this.usuario = usuario;
        this.tamanhoFonte = "medio";
        this.contraste = false;
        this.leituraVoz = false;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    // Opcional: não colocar setter para id, pois é auto gerado

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTamanhoFonte() {
        return tamanhoFonte;
    }

    public void setTamanhoFonte(String tamanhoFonte) {
        this.tamanhoFonte = tamanhoFonte;
    }

    public boolean isContraste() {
        return contraste;
    }

    public void setContraste(boolean contraste) {
        this.contraste = contraste;
    }

    public boolean isLeituraVoz() {
        return leituraVoz;
    }

    public void setLeituraVoz(boolean leituraVoz) {
        this.leituraVoz = leituraVoz;
    }

    @Override
    public String toString() {
        return "ConfiguracaoAcessibilidade {id=" + id +
                ", usuario=" + (usuario != null ? usuario.getNome() : "null") +
                ", tamanhoFonte='" + tamanhoFonte + '\'' +
                ", contraste=" + contraste +
                ", leituraVoz=" + leituraVoz + '}';
    }
}
