package com.entendomeudia.controller;

import com.entendomeudia.model.Usuario;
import com.entendomeudia.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> incluirUsuario(@RequestBody Usuario usuario) {
        String erro = validarUsuario(usuario);
        if (erro != null) {
            return ResponseEntity.badRequest().body(erro);
        }

        Usuario salvo = usuarioService.incluirUsuario(usuario);
        return ResponseEntity.status(201).body(salvo);
    }

    @GetMapping
    public ResponseEntity<Collection<Usuario>> listarUsuarios() {
        Collection<Usuario> usuarios = usuarioService.recuperarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario == null) {
            return ResponseEntity.status(404).body("Usuário não encontrado.");
        }
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario novoUsuario) {
        String erro = validarUsuario(novoUsuario);
        if (erro != null) {
            return ResponseEntity.badRequest().body(erro);
        }

        Usuario atualizado = usuarioService.atualizarUsuario(id, novoUsuario);
        if (atualizado == null) {
            return ResponseEntity.status(404).body("Usuário não encontrado para atualização.");
        }
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerUsuario(@PathVariable Long id) {
        boolean removido = usuarioService.removerUsuario(id);
        if (removido) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(404).body("Usuário não encontrado para exclusão.");
    }

    // Validação simples dos dados do usuário
    private String validarUsuario(Usuario usuario) {
        if (usuario == null) {
            return "Usuário não pode ser nulo.";
        }
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            return "O nome do usuário é obrigatório.";
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            return "O email do usuário é obrigatório.";
        }
        if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
            return "A senha do usuário é obrigatória.";
        }
        if (usuario.getTipo() == null || (!usuario.getTipo().equalsIgnoreCase("responsavel") && !usuario.getTipo().equalsIgnoreCase("principal"))) {
            return "O tipo do usuário deve ser 'responsavel' ou 'principal'.";
        }
        // Você pode incluir outras validações, como formato do email ou telefone
        return null;
    }
}
