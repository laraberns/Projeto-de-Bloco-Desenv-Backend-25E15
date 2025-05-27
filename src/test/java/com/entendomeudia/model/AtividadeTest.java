package com.entendomeudia.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AtividadeTest {

    @Test
    public void testCriacaoAtividade() {
        Atividade atividade = new Atividade("Ler livro", "08:00", "09:00");

        assertEquals("Ler livro", atividade.getNome());
        assertEquals("08:00", atividade.getHorarioInicio());
        assertEquals("09:00", atividade.getHorarioFim());
        assertEquals("pendente", atividade.getStatus());
        assertNull(atividade.getDescricao());
        assertNull(atividade.getRotina());
    }

    @Test
    public void testSetters() {
        Atividade atividade = new Atividade();
        atividade.setNome("Tomar café");
        atividade.setDescricao("Café da manhã com a família");
        atividade.setHorarioInicio("07:00");
        atividade.setHorarioFim("07:30");
        atividade.setStatus("concluida");

        assertEquals("Tomar café", atividade.getNome());
        assertEquals("Café da manhã com a família", atividade.getDescricao());
        assertEquals("07:00", atividade.getHorarioInicio());
        assertEquals("07:30", atividade.getHorarioFim());
        assertEquals("concluida", atividade.getStatus());
    }
}
