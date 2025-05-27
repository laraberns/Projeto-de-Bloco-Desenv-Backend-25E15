package com.entendomeudia.service;

import com.entendomeudia.model.Atividade;
import com.entendomeudia.model.Rotina;
import com.entendomeudia.model.Usuario;
import com.entendomeudia.repository.RotinaRepository;
import com.entendomeudia.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Commit // Remova se quiser rollback automático após cada teste
class RotinaServiceTest {

    @Autowired
    private RotinaService rotinaService;

    @Autowired
    private RotinaRepository rotinaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void testIncluirRotina() {
        Usuario usuario = new Usuario("Maria Silva", "principal", "senha123");
        usuario = usuarioRepository.save(usuario);

        Date data = new Date();
        Rotina rotina = new Rotina(data, usuario);

        Rotina salva = rotinaService.incluirRotina(rotina);

        assertThat(salva).isNotNull();
        assertThat(salva.getId()).isNotNull();
        assertThat(salva.getUsuario()).isEqualTo(usuario);
        assertThat(salva.getData()).isEqualTo(data);
    }

    @Test
    void testRecuperarRotinas() {
        Usuario u1 = usuarioRepository.save(new Usuario("João", "responsavel", "senha456"));
        Usuario u2 = usuarioRepository.save(new Usuario("Ana", "principal", "senha789"));

        Date hoje = new Date();

        rotinaRepository.save(new Rotina(hoje, u1));
        rotinaRepository.save(new Rotina(hoje, u2));

        Collection<Rotina> resultado = rotinaService.recuperarRotinas();

        assertThat(resultado).isNotEmpty();
        assertThat(resultado.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    void testRecuperarRotinasListaVazia() {
        Collection<Rotina> resultado = rotinaService.recuperarRotinas();
        assertThat(resultado).isNotNull();
    }

    @Test
    void testBuscarPorId() {
        Usuario usuario = usuarioRepository.save(new Usuario("Carlos", "principal", "senha101"));
        Date data = new Date();

        Rotina rotina = new Rotina(data, usuario);
        rotina = rotinaRepository.save(rotina);

        Rotina encontrada = rotinaService.buscarPorId(rotina.getId());

        assertThat(encontrada).isNotNull();
        assertThat(encontrada.getId()).isEqualTo(rotina.getId());
        assertThat(encontrada.getUsuario()).isEqualTo(usuario);
        assertThat(encontrada.getData()).isEqualTo(data);
    }

    @Test
    void testBuscarPorIdNaoEncontrado() {
        Rotina resultado = rotinaService.buscarPorId(9999L);
        assertThat(resultado).isNull();
    }

    @Test
    void testIncluirRotinaComAtividades() {
        Usuario usuario = usuarioRepository.save(new Usuario("Pedro", "principal", "senha202"));
        Date data = new Date();
        Rotina rotina = new Rotina(data, usuario);

        rotina.adicionarAtividade(new Atividade("Estudar", "09:00", "10:00"));
        rotina.adicionarAtividade(new Atividade("Exercícios", "18:00", "19:00"));

        Rotina salva = rotinaService.incluirRotina(rotina);

        assertThat(salva).isNotNull();
        assertThat(salva.getAtividades()).hasSize(2);
    }
}
