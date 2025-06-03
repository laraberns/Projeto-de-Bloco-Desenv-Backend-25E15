package com.entendomeudia.model;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "Atividades")
public class Atividade implements Serializable {

    // Usar Long para id, com geração automática
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private String horarioInicio;

    private String horarioFim;

    private String status = "pendente"; // pendente, concluida, cancelada

    // Relacionamento muitos-para-um com Rotina
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rotina_id") // nome da FK na tabela Atividades
    private Rotina rotina;

    // Construtores

    public Atividade() {
        // construtor padrão obrigatório para JPA
    }

    public Atividade(String nome, String horarioInicio, String horarioFim) {
        this.nome = nome;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
        this.status = "pendente";
    }

    // Getters e setters

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(String horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public String getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(String horarioFim) {
        this.horarioFim = horarioFim;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Rotina getRotina() {
        return rotina;
    }

    public void setRotina(Rotina rotina) {
        this.rotina = rotina;
    }

    @Override
    public String toString() {
        return "Atividade {id=" + id + ", nome='" + nome + "', descricao='" + descricao + "', horarioInicio='" + horarioInicio + "', horarioFim='" + horarioFim + "', status='" + status + "'}";
    }
}
