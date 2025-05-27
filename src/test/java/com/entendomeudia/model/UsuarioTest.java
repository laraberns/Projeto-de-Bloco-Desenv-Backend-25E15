package com.entendomeudia.model;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    @Test
    public void testConstrutorComNomeTipoSenha() {
        Usuario usuario = new Usuario("Maria", "principal", "senha123");

        assertEquals("Maria", usuario.getNome());
        assertEquals("principal", usuario.getTipo());
        assertEquals("senha123", usuario.getSenha());
    }

    @Test
    public void testSettersEGetters() {
        Usuario usuario = new Usuario();

        usuario.setNome("João");
        usuario.setTipo("responsavel");
        usuario.setEmail("joao@email.com");
        usuario.setTelefone("123456789");
        usuario.setSenha("minhasenha");

        assertEquals("João", usuario.getNome());
        assertEquals("responsavel", usuario.getTipo());
        assertEquals("joao@email.com", usuario.getEmail());
        assertEquals("123456789", usuario.getTelefone());
        assertEquals("minhasenha", usuario.getSenha());
    }

    @Test
    public void testAdicionarRotinas() {
        Usuario usuario = new Usuario("Carlos", "principal", "senha");
        Rotina rotina1 = new Rotina(new Date(), usuario);
        Rotina rotina2 = new Rotina(new Date(), usuario);

        usuario.setRotinas(Arrays.asList(rotina1, rotina2));

        assertEquals(2, usuario.getRotinas().size());
        assertTrue(usuario.getRotinas().contains(rotina1));
        assertTrue(usuario.getRotinas().contains(rotina2));
    }

    @Test
    public void testSetConfiguracaoAcessibilidade() {
        Usuario usuario = new Usuario("Bea", "responsavel", "123");
        ConfiguracaoAcessibilidade config = new ConfiguracaoAcessibilidade(usuario);
        usuario.setConfiguracaoAcessibilidade(config);

        assertEquals(config, usuario.getConfiguracaoAcessibilidade());
    }

    @Test
    public void testSetRelatorio() {
        Usuario usuario = new Usuario("Pedro", "principal", "senha");
        Relatorio relatorio = new Relatorio();
        relatorio.setUsuario(usuario);

        usuario.setRelatorio(relatorio);

        assertEquals(relatorio, usuario.getRelatorio());
    }

    @Test
    public void testFromMap() {
        Map<String, String> map = new HashMap<>();
        map.put("nome", "Laura");
        map.put("tipo", "responsavel");
        map.put("senha", "123456");
        map.put("email", "laura@email.com");
        map.put("telefone", "987654321");

        Usuario usuario = Usuario.fromMap(map);

        assertEquals("Laura", usuario.getNome());
        assertEquals("responsavel", usuario.getTipo());
        assertEquals("123456", usuario.getSenha());
        assertEquals("laura@email.com", usuario.getEmail());
        assertEquals("987654321", usuario.getTelefone());
    }
}
