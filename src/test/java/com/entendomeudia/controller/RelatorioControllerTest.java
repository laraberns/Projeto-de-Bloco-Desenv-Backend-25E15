package com.entendomeudia.controller;

import com.entendomeudia.model.Relatorio;
import com.entendomeudia.model.Usuario;
import com.entendomeudia.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RelatorioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testIncluirRelatorioValido() throws Exception {
        Usuario usuario = new Usuario("João", "principal", "senha");
        usuario = usuarioRepository.save(usuario);

        Relatorio relatorio = new Relatorio();
        relatorio.setUsuario(usuario);
        relatorio.setPeriodoInicio(new Date());
        relatorio.setPeriodoFim(new Date(System.currentTimeMillis() + 86400000));
        relatorio.setTotalAtividades(10);
        relatorio.setConcluidas(8);
        relatorio.setNaoRealizadas(2);

        mockMvc.perform(post("/relatorios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(relatorio)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.totalAtividades").value(10));
    }

    @Test
    void testIncluirRelatorioComUsuarioNulo() throws Exception {
        Relatorio relatorio = new Relatorio();
        relatorio.setPeriodoInicio(new Date());
        relatorio.setPeriodoFim(new Date(System.currentTimeMillis() + 86400000));

        mockMvc.perform(post("/relatorios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(relatorio)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("O usuário do relatório é obrigatório."));
    }

    @Test
    void testIncluirRelatorioComPeriodoInvalido() throws Exception {
        Usuario usuario = new Usuario("Maria", "principal", "senha");
        usuario = usuarioRepository.save(usuario);

        Relatorio relatorio = new Relatorio();
        relatorio.setUsuario(usuario);
        relatorio.setPeriodoInicio(new Date(System.currentTimeMillis() + 86400000)); // amanhã
        relatorio.setPeriodoFim(new Date()); // hoje

        mockMvc.perform(post("/relatorios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(relatorio)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("O período final não pode ser anterior ao inicial."));
    }

    @Test
    void testBuscarRelatorioNaoEncontrado() throws Exception {
        mockMvc.perform(get("/relatorios/9999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Relatório não encontrado."));
    }

    @Test
    void testRemoverRelatorioNaoEncontrado() throws Exception {
        mockMvc.perform(delete("/relatorios/9999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Relatório não encontrado para exclusão."));
    }
}
