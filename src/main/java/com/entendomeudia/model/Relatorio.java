package com.entendomeudia.model;

import java.util.*;

public class Relatorio {
    private String id;
    private Usuario usuario; // 1:1 com Usuario
    private Date periodoInicio;
    private Date periodoFim;
    private int totalAtividades;
    private int concluidas;
    private int naoRealizadas;

    public Relatorio(Usuario usuario, Date inicio, Date fim) {
        this.usuario = usuario;
        this.periodoInicio = inicio;
        this.periodoFim = fim;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTotalAtividades(int totalAtividades) {
        this.totalAtividades = totalAtividades;
    }

    public void setConcluidas(int concluidas) {
        this.concluidas = concluidas;
    }

    public void setNaoRealizadas(int naoRealizadas) {
        this.naoRealizadas = naoRealizadas;
    }

    @Override
    public String toString() {
        return "Relatorio {id='" + id + "', usuario='" + usuario.getNome() + "', periodoInicio=" + periodoInicio + ", periodoFim=" + periodoFim + ", totalAtividades=" + totalAtividades + ", concluidas=" + concluidas + ", naoRealizadas=" + naoRealizadas + "}";
    }
}