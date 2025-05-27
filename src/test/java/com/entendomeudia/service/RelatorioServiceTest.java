package com.entendomeudia.service;

import com.entendomeudia.model.Relatorio;
import com.entendomeudia.model.Usuario;
import com.entendomeudia.repository.RelatorioRepository;
import com.entendomeudia.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Commit // Remove se quiser rollback automático após os testes
class RelatorioServiceTest {

    @Autowired
    private RelatorioService relatorioService;

    @Autowired
    private RelatorioRepository relatorioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void testIncluirRelatorio() {
        Usuario usuario = new Usuario("João Silva", "principal", "senha123");
        usuario = usuarioRepository.save(usuario);

        Date inicio = new Date();
        Date fim = new Date(inicio.getTime() + 86400000);

        Relatorio relatorio = new Relatorio(usuario, inicio, fim);
        relatorio.setTotalAtividades(10);
        relatorio.setConcluidas(8);
        relatorio.setNaoRealizadas(2);

        Relatorio salvo = relatorioService.incluirRelatorio(relatorio);

        assertThat(salvo).isNotNull();
        assertThat(salvo.getId()).isNotNull();
        assertThat(salvo.getUsuario()).isEqualTo(usuario);
        assertThat(salvo.getTotalAtividades()).isEqualTo(10);
        assertThat(salvo.getConcluidas()).isEqualTo(8);
        assertThat(salvo.getNaoRealizadas()).isEqualTo(2);
    }

    @Test
    void testRecuperarRelatorios() {
        Usuario maria = new Usuario("Maria", "responsavel", "senha456");
        Usuario carlos = new Usuario("Carlos", "principal", "senha789");

        maria = usuarioRepository.save(maria);
        carlos = usuarioRepository.save(carlos);

        Date hoje = new Date();

        Relatorio r1 = new Relatorio(maria, hoje, hoje);
        Relatorio r2 = new Relatorio(carlos, hoje, hoje);

        relatorioRepository.save(r1);
        relatorioRepository.save(r2);

        Collection<Relatorio> resultado = relatorioService.recuperarRelatorios();

        assertThat(resultado).isNotEmpty();
        assertThat(resultado.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    void testBuscarPorId() {
        Usuario ana = new Usuario("Ana", "principal", "senha101");
        ana = usuarioRepository.save(ana);

        Date inicio = new Date();
        Date fim = new Date(inicio.getTime() + 86400000);

        Relatorio relatorio = new Relatorio(ana, inicio, fim);
        relatorio.setTotalAtividades(5);
        relatorio.setConcluidas(3);

        Relatorio salvo = relatorioRepository.save(relatorio);

        Relatorio encontrado = relatorioService.buscarPorId(salvo.getId());

        assertThat(encontrado).isNotNull();
        assertThat(encontrado.getUsuario()).isEqualTo(ana);
        assertThat(encontrado.getTotalAtividades()).isEqualTo(5);
        assertThat(encontrado.getConcluidas()).isEqualTo(3);
    }

    @Test
    void testBuscarPorIdNaoEncontrado() {
        Relatorio resultado = relatorioService.buscarPorId(9999L);
        assertThat(resultado).isNull();
    }
}
