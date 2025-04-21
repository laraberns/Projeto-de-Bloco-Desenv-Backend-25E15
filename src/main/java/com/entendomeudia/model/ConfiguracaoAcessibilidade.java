package com.entendomeudia.model;

public class ConfiguracaoAcessibilidade {
    private String id;
    private Usuario usuario; // 1:1 com Usuario
    private String tamanhoFonte; // pequeno, medio, grande
    private boolean contraste;
    private boolean leituraVoz;

    public ConfiguracaoAcessibilidade(Usuario usuario) {
        this.usuario = usuario;
        this.tamanhoFonte = "medio";
        this.contraste = false;
        this.leituraVoz = false;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setTamanhoFonte(String tamanhoFonte) {
        this.tamanhoFonte = tamanhoFonte;
    }

    public void setContraste(boolean contraste) {
        this.contraste = contraste;
    }

    public void setLeituraVoz(boolean leituraVoz) {
        this.leituraVoz = leituraVoz;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ConfiguracaoAcessibilidade {id='" + id + "', usuario='" + usuario.getNome() + "', tamanhoFonte='" + tamanhoFonte + "', contraste=" + contraste + ", leituraVoz=" + leituraVoz + "}";
    }
}