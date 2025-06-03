package com.entendomeudia.controller;

import com.entendomeudia.model.Rotina;
import com.entendomeudia.model.Usuario;
import com.entendomeudia.repository.RotinaRepository;
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

import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@Commit
public class RotinaControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private RotinaRepository rotinaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Usuario usuario;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        usuario = usuarioRepository.save(new Usuario("Teste Usuário", "teste", "senha123"));
    }

    private Date criarData(int ano, int mes, int dia) {
        Calendar cal = Calendar.getInstance();
        cal.set(ano, mes - 1, dia, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    @Test
    void testIncluirRotina() throws Exception {
        Rotina rotina = new Rotina();
        rotina.setUsuario(usuario);
        rotina.setData(criarData(2025, 6, 2));

        String json = objectMapper.writeValueAsString(rotina);

        mockMvc.perform(post("/rotinas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.usuario.id").value(usuario.getId()))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void testIncluirRotinaSemData() throws Exception {
        Rotina rotina = new Rotina();
        rotina.setUsuario(usuario);
        // rotina.setData(null);

        String json = objectMapper.writeValueAsString(rotina);

        mockMvc.perform(post("/rotinas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("A data da rotina é obrigatória."));
    }

    @Test
    void testIncluirRotinaSemUsuario() throws Exception {
        Rotina rotina = new Rotina();
        rotina.setData(criarData(2025, 6, 2));
        rotina.setUsuario(null);

        String json = objectMapper.writeValueAsString(rotina);

        mockMvc.perform(post("/rotinas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("O usuário da rotina é obrigatório."));
    }

    @Test
    void testListarRotinas() throws Exception {
        Rotina rotina = new Rotina();
        rotina.setUsuario(usuario);
        rotina.setData(criarData(2025, 6, 2));
        rotinaRepository.save(rotina);

        mockMvc.perform(get("/rotinas"))
                .andExpect(status().isOk());
    }

    @Test
    void testBuscarPorId() throws Exception {
        Rotina rotina = new Rotina();
        rotina.setUsuario(usuario);
        rotina.setData(criarData(2025, 6, 2));
        rotina = rotinaRepository.save(rotina);

        mockMvc.perform(get("/rotinas/" + rotina.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(rotina.getId()))
                .andExpect(jsonPath("$.usuario.id").value(usuario.getId()));
    }

    @Test
    void testBuscarPorIdNaoEncontrado() throws Exception {
        mockMvc.perform(get("/rotinas/999999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Rotina não encontrada."));
    }

    @Test
    void testAtualizarRotina() throws Exception {
        Rotina rotina = new Rotina();
        rotina.setUsuario(usuario);
        rotina.setData(criarData(2025, 6, 2));
        rotina = rotinaRepository.save(rotina);

        Rotina atualizacao = new Rotina();
        atualizacao.setUsuario(usuario);
        atualizacao.setData(criarData(2025, 6, 3));

        String json = objectMapper.writeValueAsString(atualizacao);

        mockMvc.perform(put("/rotinas/" + rotina.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void testAtualizarRotinaSemData() throws Exception {
        Rotina rotina = new Rotina();
        rotina.setUsuario(usuario);
        rotina.setData(criarData(2025, 6, 2));
        rotina = rotinaRepository.save(rotina);

        Rotina atualizacao = new Rotina();
        atualizacao.setUsuario(usuario);
        atualizacao.setData(null);

        String json = objectMapper.writeValueAsString(atualizacao);

        mockMvc.perform(put("/rotinas/" + rotina.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("A data da rotina é obrigatória."));
    }

    @Test
    void testAtualizarRotinaSemUsuario() throws Exception {
        Rotina rotina = new Rotina();
        rotina.setUsuario(usuario);
        rotina.setData(criarData(2025, 6, 2));
        rotina = rotinaRepository.save(rotina);

        Rotina atualizacao = new Rotina();
        atualizacao.setUsuario(null);
        atualizacao.setData(criarData(2025, 6, 3));

        String json = objectMapper.writeValueAsString(atualizacao);

        mockMvc.perform(put("/rotinas/" + rotina.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("O usuário da rotina é obrigatório."));
    }

    @Test
    void testAtualizarRotinaNaoEncontrada() throws Exception {
        Rotina atualizacao = new Rotina();
        atualizacao.setUsuario(usuario);
        atualizacao.setData(criarData(2025, 6, 3));

        String json = objectMapper.writeValueAsString(atualizacao);

        mockMvc.perform(put("/rotinas/999999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Rotina não encontrada para atualização."));
    }

    @Test
    void testRemoverRotina() throws Exception {
        Rotina rotina = new Rotina();
        rotina.setUsuario(usuario);
        rotina.setData(criarData(2025, 6, 2));
        rotina = rotinaRepository.save(rotina);

        mockMvc.perform(delete("/rotinas/" + rotina.getId()))
                .andExpect(status().isNoContent());

        assertThat(rotinaRepository.findById(rotina.getId())).isEmpty();
    }

    @Test
    void testRemoverRotinaNaoEncontrada() throws Exception {
        mockMvc.perform(delete("/rotinas/999999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Rotina não encontrada para exclusão."));
    }
}
