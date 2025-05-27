package com.entendomeudia.model;

import jakarta.persistence.*; // ou javax.persistence dependendo do seu projeto
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Relatorios")
public class Relatorio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento 1:1 com Usuario
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", unique = true)
    private Usuario usuario;

    @Temporal(TemporalType.DATE)
    private Date periodoInicio;

    @Temporal(TemporalType.DATE)
    private Date periodoFim;

    private int totalAtividades;

    private int concluidas;

    private int naoRealizadas;

    public Relatorio() {
        // construtor padrão para JPA
    }

    public Relatorio(Usuario usuario, Date periodoInicio, Date periodoFim) {
        this.usuario = usuario;
        this.periodoInicio = periodoInicio;
        this.periodoFim = periodoFim;
    }

    // Getters e setters

    public Long getId() {
        return id;
    }

    // Opcional: sem setter para id

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getPeriodoInicio() {
        return periodoInicio;
    }

    public void setPeriodoInicio(Date periodoInicio) {
        this.periodoInicio = periodoInicio;
    }

    public Date getPeriodoFim() {
        return periodoFim;
    }

    public void setPeriodoFim(Date periodoFim) {
        this.periodoFim = periodoFim;
    }

    public int getTotalAtividades() {
        return totalAtividades;
    }

    public void setTotalAtividades(int totalAtividades) {
        this.totalAtividades = totalAtividades;
    }

    public int getConcluidas() {
        return concluidas;
    }

    public void setConcluidas(int concluidas) {
        this.concluidas = concluidas;
    }

    public int getNaoRealizadas() {
        return naoRealizadas;
    }

    public void setNaoRealizadas(int naoRealizadas) {
        this.naoRealizadas = naoRealizadas;
    }

    @Override
    public String toString() {
        return "Relatorio {id=" + id +
                ", usuario=" + (usuario != null ? usuario.getNome() : "null") +
                ", periodoInicio=" + periodoInicio +
                ", periodoFim=" + periodoFim +
                ", totalAtividades=" + totalAtividades +
                ", concluidas=" + concluidas +
                ", naoRealizadas=" + naoRealizadas +
                '}';
    }
}
