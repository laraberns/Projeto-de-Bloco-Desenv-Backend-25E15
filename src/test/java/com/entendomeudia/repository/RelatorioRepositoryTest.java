package com.entendomeudia.repository;

import com.entendomeudia.model.Relatorio;
import com.entendomeudia.model.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RelatorioRepositoryTest {

    @Autowired
    private RelatorioRepository relatorioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Salvar e buscar Relatorio")
    void testSalvarEBuscarRelatorio() {
        // Cria e salva usuário
        Usuario usuario = new Usuario("Ana", "principal", "senhaSegura");
        usuario = usuarioRepository.save(usuario);
        usuarioRepository.flush();

        // Datas para período
        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.MAY, 1);
        Date inicio = cal.getTime();
        cal.set(2025, Calendar.MAY, 31);
        Date fim = cal.getTime();

        // Cria relatorio
        Relatorio relatorio = new Relatorio();
        relatorio.setUsuario(usuario);
        relatorio.setPeriodoInicio(inicio);
        relatorio.setPeriodoFim(fim);
        relatorio.setTotalAtividades(20);
        relatorio.setConcluidas(15);
        relatorio.setNaoRealizadas(5);

        // Salva relatorio
        relatorio = relatorioRepository.save(relatorio);
        relatorioRepository.flush();

        // Busca todos relatórios
        List<Relatorio> todos = relatorioRepository.findAll();

        // Asserts
        assertThat(todos).hasSize(1);
        Relatorio encontrado = todos.get(0);
        assertThat(encontrado.getUsuario().getId()).isEqualTo(usuario.getId());
        assertThat(encontrado.getPeriodoInicio()).isEqualTo(inicio);
        assertThat(encontrado.getPeriodoFim()).isEqualTo(fim);
        assertThat(encontrado.getTotalAtividades()).isEqualTo(20);
        assertThat(encontrado.getConcluidas()).isEqualTo(15);
        assertThat(encontrado.getNaoRealizadas()).isEqualTo(5);
    }
}
