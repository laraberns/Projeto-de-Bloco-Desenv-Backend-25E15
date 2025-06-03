package com.entendomeudia.controller;

import com.entendomeudia.model.ConfiguracaoAcessibilidade;
import com.entendomeudia.model.Usuario;
import com.entendomeudia.repository.ConfiguracaoAcessibilidadeRepository;
import com.entendomeudia.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@Commit
class ConfiguracaoAcessibilidadeControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ConfiguracaoAcessibilidadeRepository configRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Usuario usuario;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        usuario = usuarioRepository.save(new Usuario("Teste Usuário", "principal", "senha123"));
    }

    @Test
    void testIncluirConfiguracao() throws Exception {
        ConfiguracaoAcessibilidade config = new ConfiguracaoAcessibilidade();
        config.setUsuario(usuario);
        config.setContraste(true);
        config.setLeituraVoz(false);
        config.setTamanhoFonte("16"); // exemplo de tamanho de fonte

        String json = objectMapper.writeValueAsString(config);

        mockMvc.perform(post("/configuracoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.contraste").value(true))
                .andExpect(jsonPath("$.leituraVoz").value(false))
                .andExpect(jsonPath("$.tamanhoFonte").value("16"))
                .andExpect(jsonPath("$.usuario.id").value(usuario.getId()));
    }

    @Test
    void testListarConfiguracoes() throws Exception {
        // Criar uma configuração para garantir que existe algo no banco
        ConfiguracaoAcessibilidade config = new ConfiguracaoAcessibilidade();
        config.setUsuario(usuario);
        configRepository.save(config);

        mockMvc.perform(get("/configuracoes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").exists());
    }

    @Test
    void testBuscarPorUsuarioId() throws Exception {
        ConfiguracaoAcessibilidade config = new ConfiguracaoAcessibilidade();
        config.setUsuario(usuario);
        configRepository.save(config);

        mockMvc.perform(get("/configuracoes/usuario/" + usuario.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.usuario.id").value(usuario.getId()));
    }

    @Test
    void testBuscarPorUsuarioIdNaoEncontrado() throws Exception {
        mockMvc.perform(get("/configuracoes/usuario/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAtualizarConfiguracao() throws Exception {
        ConfiguracaoAcessibilidade config = new ConfiguracaoAcessibilidade();
        config.setUsuario(usuario);
        config.setContraste(false);
        config.setLeituraVoz(false);
        config.setTamanhoFonte("14");
        config = configRepository.save(config);

        ConfiguracaoAcessibilidade atualizacao = new ConfiguracaoAcessibilidade();
        atualizacao.setContraste(true);
        atualizacao.setLeituraVoz(true);
        atualizacao.setTamanhoFonte("18");
        atualizacao.setUsuario(usuario); // geralmente manter o usuário

        String json = objectMapper.writeValueAsString(atualizacao);

        mockMvc.perform(put("/configuracoes/" + config.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contraste").value(true))
                .andExpect(jsonPath("$.leituraVoz").value(true))
                .andExpect(jsonPath("$.tamanhoFonte").value("18"));
    }

    @Test
    void testRemoverConfiguracao() throws Exception {
        ConfiguracaoAcessibilidade config = new ConfiguracaoAcessibilidade();
        config.setUsuario(usuario);
        config = configRepository.save(config);

        mockMvc.perform(delete("/configuracoes/" + config.getId()))
                .andExpect(status().isNoContent());

        assertThat(configRepository.findById(config.getId())).isEmpty();
    }

    @Test
    void testRemoverConfiguracaoNaoEncontrada() throws Exception {
        mockMvc.perform(delete("/configuracoes/999999"))
                .andExpect(status().isNotFound());
    }
}
