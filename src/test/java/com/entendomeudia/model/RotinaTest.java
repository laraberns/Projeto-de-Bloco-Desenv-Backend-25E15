package com.entendomeudia.model;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class RotinaTest {

    @Test
    public void testConstrutorComDataEUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNome("Ana");

        Date data = new Date();
        Rotina rotina = new Rotina(data, usuario);

        assertEquals(data, rotina.getData());
        assertEquals(usuario, rotina.getUsuario());
        assertNotNull(rotina.getAtividades());
        assertTrue(rotina.getAtividades().isEmpty());
    }

    @Test
    public void testAdicionarAtividade() {
        Usuario usuario = new Usuario();
        usuario.setNome("Carlos");

        Rotina rotina = new Rotina(new Date(), usuario);

        Atividade atividade = new Atividade("Ler", "08:00", "09:00");
        rotina.adicionarAtividade(atividade);

        assertEquals(1, rotina.getAtividades().size());
        assertEquals(rotina, atividade.getRotina());
    }

    @Test
    public void testRemoverAtividade() {
        Usuario usuario = new Usuario();
        Rotina rotina = new Rotina(new Date(), usuario);

        Atividade atividade = new Atividade("Estudar", "10:00", "11:00");
        rotina.adicionarAtividade(atividade);

        rotina.removerAtividade(atividade);

        assertEquals(0, rotina.getAtividades().size());
        assertNull(atividade.getRotina());
    }

    @Test
    public void testSettersEGetters() {
        Usuario usuario = new Usuario();
        usuario.setNome("Beatriz");

        Rotina rotina = new Rotina();
        Date data = new Date();

        rotina.setData(data);
        rotina.setUsuario(usuario);

        assertEquals(data, rotina.getData());
        assertEquals(usuario, rotina.getUsuario());
    }
}
