package com.entendomeudia.repository;

import com.entendomeudia.model.ConfiguracaoAcessibilidade;
import com.entendomeudia.model.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ConfiguracaoAcessibilidadeRepositoryTest {

    @Autowired
    private ConfiguracaoAcessibilidadeRepository configuracaoAcessibilidadeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Salvar e buscar ConfiguracaoAcessibilidade pelo usuarioId")
    void testFindByUsuarioId() {
        // Cria e salva usuario
        Usuario usuario = new Usuario("João", "principal", "senha123");
        usuario = usuarioRepository.save(usuario);
        usuarioRepository.flush();

        // Cria ConfiguracaoAcessibilidade vinculada ao usuario
        ConfiguracaoAcessibilidade config = new ConfiguracaoAcessibilidade();
        config.setUsuario(usuario);
        config.setTamanhoFonte("medio");
        config.setContraste(true);
        config.setLeituraVoz(false);
        config = configuracaoAcessibilidadeRepository.save(config);
        configuracaoAcessibilidadeRepository.flush();

        // Testa busca por usuarioId
        ConfiguracaoAcessibilidade encontrada = configuracaoAcessibilidadeRepository.findByUsuarioId(usuario.getId());

        assertThat(encontrada).isNotNull();
        assertThat(encontrada.getUsuario().getId()).isEqualTo(usuario.getId());
        assertThat(encontrada.getTamanhoFonte()).isEqualTo("medio");
        assertThat(encontrada.isContraste()).isTrue();
        assertThat(encontrada.isLeituraVoz()).isFalse();
    }
}
