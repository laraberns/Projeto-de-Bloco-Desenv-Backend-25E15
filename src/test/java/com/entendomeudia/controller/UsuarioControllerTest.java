package com.entendomeudia.controller;

import com.entendomeudia.model.Usuario;
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
public class UsuarioControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Usuario usuario;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        usuario = new Usuario();
        usuario.setNome("Usuário Teste");
        usuario.setTipo("principal");
        usuario.setEmail("teste@email.com");
        usuario.setTelefone("123456789");
        usuario.setSenha("senha123");

        usuario = usuarioRepository.save(usuario);
    }

    @Test
    void testIncluirUsuarioValido() throws Exception {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome("Novo Usuário");
        novoUsuario.setTipo("responsavel");
        novoUsuario.setEmail("novo@email.com");
        novoUsuario.setTelefone("987654321");
        novoUsuario.setSenha("123456");

        String json = objectMapper.writeValueAsString(novoUsuario);

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("Novo Usuário"))
                .andExpect(jsonPath("$.email").value("novo@email.com"));
    }

    @Test
    void testIncluirUsuarioInvalido() throws Exception {
        Usuario novoUsuario = new Usuario(); // todos campos vazios
        String json = objectMapper.writeValueAsString(novoUsuario);

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("O nome do usuário é obrigatório."));
    }

    @Test
    void testIncluirUsuarioTipoInvalido() throws Exception {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome("Nome");
        novoUsuario.setTipo("secundario"); // inválido
        novoUsuario.setEmail("email@teste.com");
        novoUsuario.setSenha("senha123");

        String json = objectMapper.writeValueAsString(novoUsuario);

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("O tipo do usuário deve ser 'responsavel' ou 'principal'."));
    }

    @Test
    void testListarUsuarios() throws Exception {
        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk());
    }

    @Test
    void testBuscarPorId() throws Exception {
        mockMvc.perform(get("/usuarios/" + usuario.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(usuario.getId()))
                .andExpect(jsonPath("$.nome").value(usuario.getNome()));
    }

    @Test
    void testBuscarPorIdNaoEncontrado() throws Exception {
        mockMvc.perform(get("/usuarios/999999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuário não encontrado."));
    }

    @Test
    void testAtualizarUsuarioValido() throws Exception {
        Usuario atualizacao = new Usuario();
        atualizacao.setNome("Nome Atualizado");
        atualizacao.setTipo("principal");
        atualizacao.setEmail("atualizado@email.com");
        atualizacao.setTelefone("111222333");
        atualizacao.setSenha("novasenha");

        String json = objectMapper.writeValueAsString(atualizacao);

        mockMvc.perform(put("/usuarios/" + usuario.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Nome Atualizado"))
                .andExpect(jsonPath("$.email").value("atualizado@email.com"))
                .andExpect(jsonPath("$.telefone").value("111222333"));
    }

    @Test
    void testAtualizarUsuarioInvalido() throws Exception {
        Usuario atualizacao = new Usuario();
        // nome faltando
        atualizacao.setTipo("principal");
        atualizacao.setEmail("atualizado@email.com");
        atualizacao.setTelefone("111222333");
        atualizacao.setSenha("novasenha");

        String json = objectMapper.writeValueAsString(atualizacao);

        mockMvc.perform(put("/usuarios/" + usuario.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("O nome do usuário é obrigatório."));
    }

    @Test
    void testAtualizarUsuarioNaoEncontrado() throws Exception {
        Usuario atualizacao = new Usuario();
        atualizacao.setNome("Nome Atualizado");
        atualizacao.setTipo("principal");
        atualizacao.setEmail("atualizado@email.com");
        atualizacao.setTelefone("111222333");
        atualizacao.setSenha("novasenha");

        String json = objectMapper.writeValueAsString(atualizacao);

        mockMvc.perform(put("/usuarios/999999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuário não encontrado para atualização."));
    }

    @Test
    void testRemoverUsuario() throws Exception {
        mockMvc.perform(delete("/usuarios/" + usuario.getId()))
                .andExpect(status().isNoContent());

        assertThat(usuarioRepository.findById(usuario.getId())).isEmpty();
    }

    @Test
    void testRemoverUsuarioNaoEncontrado() throws Exception {
        mockMvc.perform(delete("/usuarios/999999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuário não encontrado para exclusão."));
    }
}
