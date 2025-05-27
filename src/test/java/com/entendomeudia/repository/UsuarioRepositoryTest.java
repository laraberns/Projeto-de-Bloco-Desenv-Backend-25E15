package com.entendomeudia.repository;

import com.entendomeudia.model.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Salvar e buscar usuário")
    void testSalvarEBuscarUsuario() {
        // Criar novo usuário
        Usuario usuario = new Usuario("Maria", "principal", "senha123");
        usuario.setEmail("maria@email.com");
        usuario.setTelefone("123456789");

        // Salvar usuário
        Usuario salvo = usuarioRepository.save(usuario);
        usuarioRepository.flush(); // força a gravação no banco imediatamente

        // Buscar todos usuários
        List<Usuario> usuarios = usuarioRepository.findAll();

        // Verificar se o usuário foi salvo e está presente na lista
        assertThat(usuarios).isNotEmpty();
        assertThat(usuarios).contains(salvo);

        // Verificar dados específicos
        Usuario encontrado = usuarios.get(0);
        assertThat(encontrado.getNome()).isEqualTo("Maria");
        assertThat(encontrado.getTipo()).isEqualTo("principal");
        assertThat(encontrado.getEmail()).isEqualTo("maria@email.com");
        assertThat(encontrado.getTelefone()).isEqualTo("123456789");
    }
}
