package com.entendomeudia;

import com.entendomeudia.model.*;
import com.entendomeudia.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Calendar;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

public class SistemaIntegracaoCompletoTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ConfiguracaoAcessibilidadeService configuracaoService;

    @Autowired
    private AtividadeService atividadeService;

    @Autowired
    private RotinaService rotinaService;

    @Autowired
    private RelatorioService relatorioService;

    @Test
    public void testFluxoCompletoSistema() {
        // 1. Criação e validação de usuário
        Usuario usuario = criarUsuarioValido();
        Usuario usuarioSalvo = usuarioService.incluirUsuario(usuario);
        validarUsuarioSalvo(usuarioSalvo);

        // 2. Configuração de acessibilidade
        ConfiguracaoAcessibilidade config = criarConfiguracaoAcessibilidade(usuarioSalvo);
        ConfiguracaoAcessibilidade configSalva = configuracaoService.incluirConfiguracao(config);
        validarConfiguracaoSalva(usuarioSalvo, configSalva);

        // 3. Criação de rotina
        Rotina rotina = criarRotina(usuarioSalvo);
        Rotina rotinaSalva = rotinaService.incluirRotina(rotina);
        validarRotinaSalva(rotinaSalva);

        // 4. Criação de atividades associadas à rotina
        Atividade atividadeManha = criarAtividadeManha(rotinaSalva);
        Atividade atividadeTarde = criarAtividadeTarde(rotinaSalva);

        Atividade atividadeManhaSalva = atividadeService.incluirAtividade(atividadeManha);
        Atividade atividadeTardeSalva = atividadeService.incluirAtividade(atividadeTarde);

        validarAtividadesSalvas(rotinaSalva, atividadeManhaSalva, atividadeTardeSalva);

        // 5. Geração de relatório semanal
        Relatorio relatorio = criarRelatorio(usuarioSalvo);
        Relatorio relatorioSalvo = relatorioService.incluirRelatorio(relatorio);
        validarRelatorioSalvo(relatorioSalvo);

        // 6. Verificação final da consistência dos dados
        verificarConsistenciaDados(usuarioSalvo, rotinaSalva);
    }

    private Usuario criarUsuarioValido() {
        Usuario usuario = new Usuario();
        usuario.setNome("Maria Oliveira");
        usuario.setEmail("maria@example.com");
        usuario.setSenha("senhaSegura123");
        usuario.setTipo("principal");
        return usuario;
    }

    private ConfiguracaoAcessibilidade criarConfiguracaoAcessibilidade(Usuario usuario) {
        ConfiguracaoAcessibilidade config = new ConfiguracaoAcessibilidade();
        config.setUsuario(usuario);
        config.setTamanhoFonte("medio");
        config.setContraste(true);
        config.setLeituraVoz(false);
        return config;
    }

    private Rotina criarRotina(Usuario usuario) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        Date amanha = cal.getTime();

        Rotina rotina = new Rotina();
        rotina.setData(amanha);
        rotina.setUsuario(usuario);
        return rotina;
    }

    private Atividade criarAtividadeManha(Rotina rotina) {
        Atividade atividade = new Atividade();
        atividade.setNome("Terapia Ocupacional");
        atividade.setDescricao("Sessão com terapeuta");
        atividade.setHorarioInicio("09:00");
        atividade.setHorarioFim("10:00");
        atividade.setStatus("pendente");
        atividade.setRotina(rotina);
        return atividade;
    }

    private Atividade criarAtividadeTarde(Rotina rotina) {
        Atividade atividade = new Atividade();
        atividade.setNome("Exercícios Físicos");
        atividade.setDescricao("Alongamento e caminhada");
        atividade.setHorarioInicio("15:00");
        atividade.setHorarioFim("16:00");
        atividade.setStatus("pendente");
        atividade.setRotina(rotina);
        return atividade;
    }

    private Relatorio criarRelatorio(Usuario usuario) {
        Calendar cal = Calendar.getInstance();
        Date hoje = cal.getTime();
        cal.add(Calendar.DATE, 7);
        Date semanaQueVem = cal.getTime();

        Relatorio relatorio = new Relatorio();
        relatorio.setUsuario(usuario);
        relatorio.setPeriodoInicio(hoje);
        relatorio.setPeriodoFim(semanaQueVem);
        return relatorio;
    }

    private void validarUsuarioSalvo(Usuario usuarioSalvo) {
        assertNotNull(usuarioSalvo.getId(), "ID do usuário não foi gerado");
        assertEquals("Maria Oliveira", usuarioSalvo.getNome(), "Nome do usuário não foi salvo corretamente");
    }

    private void validarConfiguracaoSalva(Usuario usuario, ConfiguracaoAcessibilidade config) {
        assertEquals(usuario.getId(), config.getUsuario().getId(), "Configuração não vinculada ao usuário correto");
        assertEquals("medio", config.getTamanhoFonte(), "Tamanho da fonte não foi salvo corretamente");
    }

    private void validarRotinaSalva(Rotina rotina) {
        assertNotNull(rotina.getId(), "ID da rotina não foi gerado");
        assertNotNull(rotina.getData(), "Data da rotina não foi definida");
    }

    private void validarAtividadesSalvas(Rotina rotina, Atividade... atividades) {
        assertEquals(2, atividadeService.obterLista().size(), "Deveria haver 2 atividades cadastradas");
        for (Atividade atividade : atividades) {
            assertNotNull(atividade.getRotina(), "Atividade deve ter uma rotina associada");
            assertEquals(rotina.getId(), atividade.getRotina().getId(), "Atividade não vinculada à rotina correta");
        }
    }

    private void validarRelatorioSalvo(Relatorio relatorio) {
        assertNotNull(relatorio.getId(), "Relatório não foi persistido");
        assertNotNull(relatorio.getPeriodoInicio(), "Período inicial não foi definido");
        assertNotNull(relatorio.getPeriodoFim(), "Período final não foi definido");
    }

    private void verificarConsistenciaDados(Usuario usuario, Rotina rotina) {
        // Verifica usuário
        Usuario usuarioRecuperado = usuarioService.buscarPorId(usuario.getId());
        assertNotNull(usuarioRecuperado, "Usuário não encontrado");

        // Verifica configurações
        ConfiguracaoAcessibilidade configRecuperada =
                configuracaoService.buscarPorUsuarioId(usuario.getId());
        assertNotNull(configRecuperada, "Configurações não encontradas");

        // Verifica rotina
        Rotina rotinaRecuperada = rotinaService.buscarPorId(rotina.getId());
        assertNotNull(rotinaRecuperada, "Rotina não encontrada");
        assertEquals(usuario.getId(), rotinaRecuperada.getUsuario().getId(),
                "Rotina não vinculada ao usuário correto");

        // Verifica relatórios
        assertFalse(relatorioService.recuperarRelatorios().isEmpty(),
                "Deveria existir pelo menos 1 relatório");
    }
}