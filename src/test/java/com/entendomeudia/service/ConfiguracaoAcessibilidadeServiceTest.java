package com.entendomeudia.service;

import com.entendomeudia.model.ConfiguracaoAcessibilidade;
import com.entendomeudia.model.Usuario;
import com.entendomeudia.repository.ConfiguracaoAcessibilidadeRepository;
import com.entendomeudia.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Commit // opcional, para persistir de fato (sem rollback)
class ConfiguracaoAcessibilidadeServiceTest {

    @Autowired
    private ConfiguracaoAcessibilidadeService service;

    @Autowired
    private ConfiguracaoAcessibilidadeRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void testIncluirConfiguracao() {
        Usuario usuario = new Usuario("João", "principal", "senha123");
        usuario = usuarioRepository.save(usuario);

        ConfiguracaoAcessibilidade config = new ConfiguracaoAcessibilidade(usuario);
        config.setTamanhoFonte("grande");
        config.setContraste(true);
        config.setLeituraVoz(false);

        ConfiguracaoAcessibilidade salvo = service.incluirConfiguracao(config);

        assertThat(salvo).isNotNull();
        assertThat(salvo.getTamanhoFonte()).isEqualTo("grande");
        assertThat(salvo.isContraste()).isTrue();
        assertThat(salvo.isLeituraVoz()).isFalse();
        assertThat(salvo.getUsuario()).isEqualTo(usuario);
    }

    @Test
    void testRecuperarConfiguracoes() {
        Usuario usuario = new Usuario("Maria", "responsavel", "senha456");
        usuario = usuarioRepository.save(usuario);

        ConfiguracaoAcessibilidade config = new ConfiguracaoAcessibilidade(usuario);
        config.setTamanhoFonte("medio");
        config.setContraste(false);
        config.setLeituraVoz(true);
        repository.save(config);

        List<ConfiguracaoAcessibilidade> lista = (List<ConfiguracaoAcessibilidade>) service.recuperarConfiguracoes();

        assertThat(lista).isNotEmpty();
        assertThat(lista).hasSize(1);
        assertThat(lista.get(0).getUsuario()).isEqualTo(usuario);
    }

    @Test
    void testBuscarPorUsuarioId() {
        Usuario usuario = new Usuario("Carlos", "principal", "senha789");
        usuario = usuarioRepository.save(usuario);

        ConfiguracaoAcessibilidade config = new ConfiguracaoAcessibilidade(usuario);
        config.setLeituraVoz(true);
        repository.save(config);

        ConfiguracaoAcessibilidade encontrada = service.buscarPorUsuarioId(usuario.getId());

        assertThat(encontrada).isNotNull();
        assertThat(encontrada.isLeituraVoz()).isTrue();
        assertThat(encontrada.getUsuario()).isEqualTo(usuario);
    }

    @Test
    void testBuscarPorUsuarioIdNaoEncontrado() {
        ConfiguracaoAcessibilidade encontrada = service.buscarPorUsuarioId(9999L);
        assertThat(encontrada).isNull();
    }
}
