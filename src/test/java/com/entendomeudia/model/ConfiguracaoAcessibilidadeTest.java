package com.entendomeudia.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConfiguracaoAcessibilidadeTest {

	@Test
	public void testCriacaoComUsuario() {
		Usuario usuario = new Usuario();
		usuario.setNome("Maria");

		ConfiguracaoAcessibilidade config = new ConfiguracaoAcessibilidade(usuario);

		assertEquals(usuario, config.getUsuario());
		assertEquals("medio", config.getTamanhoFonte());
		assertFalse(config.isContraste());
		assertFalse(config.isLeituraVoz());
	}

	@Test
	public void testSetters() {
		Usuario usuario = new Usuario();
		usuario.setNome("João");

		ConfiguracaoAcessibilidade config = new ConfiguracaoAcessibilidade();
		config.setUsuario(usuario);
		config.setTamanhoFonte("grande");
		config.setContraste(true);
		config.setLeituraVoz(true);

		assertEquals(usuario, config.getUsuario());
		assertEquals("grande", config.getTamanhoFonte());
		assertTrue(config.isContraste());
		assertTrue(config.isLeituraVoz());
	}
}
