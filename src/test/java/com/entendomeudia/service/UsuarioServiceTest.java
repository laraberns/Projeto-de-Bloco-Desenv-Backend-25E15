package com.entendomeudia.service;

import com.entendomeudia.model.Usuario;
import com.entendomeudia.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void testIncluirUsuario() {
        Usuario usuario = new Usuario("João Silva", "principal", "senha123");
        usuario.setEmail("joao@email.com");
        usuario.setTelefone("11999999999");

        Usuario salvo = usuarioService.incluirUsuario(usuario);

        assertThat(salvo).isNotNull();
        assertThat(salvo.getId()).isNotNull();
        assertThat(salvo.getNome()).isEqualTo("João Silva");
        assertThat(salvo.getEmail()).isEqualTo("joao@email.com");
        assertThat(salvo.getTelefone()).isEqualTo("11999999999");
    }

    @Test
    void testRecuperarUsuarios() {
        usuarioRepository.save(new Usuario("Maria", "responsavel", "senha456"));
        usuarioRepository.save(new Usuario("Carlos", "principal", "senha789"));

        Collection<Usuario> resultado = usuarioService.recuperarUsuarios();

        assertThat(resultado).isNotEmpty();
        assertThat(resultado.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    void testRecuperarUsuariosListaVazia() {
        Collection<Usuario> resultado = usuarioService.recuperarUsuarios();

        assertThat(resultado).isNotNull();
        assertThat(resultado).isInstanceOf(Collection.class);
    }

    @Test
    void testBuscarPorId() {
        Usuario usuario = new Usuario("Ana", "principal", "senha101");
        usuario.setEmail("ana@email.com");

        usuario = usuarioRepository.save(usuario);

        Usuario encontrado = usuarioService.buscarPorId(usuario.getId());

        assertThat(encontrado).isNotNull();
        assertThat(encontrado.getNome()).isEqualTo("Ana");
        assertThat(encontrado.getEmail()).isEqualTo("ana@email.com");
    }

    @Test
    void testBuscarPorIdNaoEncontrado() {
        Usuario resultado = usuarioService.buscarPorId(9999L);
        assertThat(resultado).isNull();
    }

    @Test
    void testIncluirUsuarioComDadosMinimos() {
        Usuario usuario = new Usuario("Minimal", "principal", "senha123");

        Usuario salvo = usuarioService.incluirUsuario(usuario);

        assertThat(salvo).isNotNull();
        assertThat(salvo.getId()).isNotNull();
        assertThat(salvo.getNome()).isEqualTo("Minimal");
        assertThat(salvo.getEmail()).isNull(); // não foi setado
    }
}
