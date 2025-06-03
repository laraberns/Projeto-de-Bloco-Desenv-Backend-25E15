package com.entendomeudia.controller;

import com.entendomeudia.model.Atividade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AtividadeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testIncluirEListarAtividade() throws Exception {
        Atividade atividade = new Atividade("Estudar", "09:00", "10:00");

        // Inclui
        mockMvc.perform(post("/atividades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(atividade)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("Estudar"));

        // Lista
        mockMvc.perform(get("/atividades"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Estudar"));
    }

    @Test
    void testBuscarPorId() throws Exception {
        Atividade atividade = new Atividade("Ler", "11:00", "12:00");

        String response = mockMvc.perform(post("/atividades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(atividade)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Atividade salvo = objectMapper.readValue(response, Atividade.class);

        mockMvc.perform(get("/atividades/" + salvo.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Ler"));
    }

    @Test
    void testAtualizarAtividade() throws Exception {
        Atividade atividade = new Atividade("Dormir", "22:00", "23:00");

        String response = mockMvc.perform(post("/atividades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(atividade)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Atividade salvo = objectMapper.readValue(response, Atividade.class);

        salvo.setNome("Dormir Bem");
        salvo.setHorarioInicio("23:00");
        salvo.setHorarioFim("23:30");

        mockMvc.perform(put("/atividades/" + salvo.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(salvo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Dormir Bem"))
                .andExpect(jsonPath("$.horarioInicio").value("23:00"))
                .andExpect(jsonPath("$.horarioFim").value("23:30"));
    }

    @Test
    void testDeletarAtividade() throws Exception {
        Atividade atividade = new Atividade("Correr", "06:00", "07:00");

        String response = mockMvc.perform(post("/atividades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(atividade)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Atividade salvo = objectMapper.readValue(response, Atividade.class);

        mockMvc.perform(delete("/atividades/" + salvo.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/atividades/" + salvo.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testInsercaoInvalida() throws Exception {
        // Falta nome
        Atividade atividadeInvalida = new Atividade(null, "08:00", "09:00");

        mockMvc.perform(post("/atividades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(atividadeInvalida)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("O nome da atividade é obrigatório."));
    }

    @Test
    void testAtualizacaoComCamposFaltando() throws Exception {
        Atividade atividade = new Atividade("Jogar", "15:00", "16:00");

        String response = mockMvc.perform(post("/atividades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(atividade)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Atividade salvo = objectMapper.readValue(response, Atividade.class);

        // Zerar o nome para simular campo inválido
        salvo.setNome("");

        mockMvc.perform(put("/atividades/" + salvo.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(salvo)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("O nome da atividade é obrigatório."));
    }

    @Test
    void testDeletarInexistente() throws Exception {
        mockMvc.perform(delete("/atividades/999999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Atividade não encontrada para exclusão."));
    }
}
