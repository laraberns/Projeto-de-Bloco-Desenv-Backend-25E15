package com.entendomeudia.model;

public class Atividade {
    private String id;
    private String nome;
    private String descricao;
    private String horarioInicio;
    private String horarioFim;
    private String status; // pendente, concluida, cancelada

    private Rotina rotina; // N:1 com Rotina

    public Atividade(String id, String nome, String horarioInicio, String horarioFim) {
        this.id = id;
        this.nome = nome;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
        this.status = "pendente";
    }

    public String getId() {
        return id;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setRotina(Rotina rotina) {
        this.rotina = rotina;
    }

    @Override
    public String toString() {
        return "Atividade {id='" + id + "', nome='" + nome + "', descricao='" + descricao + "', horarioInicio='" + horarioInicio + "', horarioFim='" + horarioFim + "', status='" + status + "'}";
    }
}