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
    public ResponseEntity<Usuario> incluirUsuario(@RequestBody Usuario usuario) {
        Usuario salvo = usuarioService.incluirUsuario(usuario);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping
    public ResponseEntity<Collection<Usuario>> listarUsuarios() {
        Collection<Usuario> usuarios = usuarioService.recuperarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario novoUsuario) {
        Usuario atualizado = usuarioService.atualizarUsuario(id, novoUsuario);
        if (atualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerUsuario(@PathVariable Long id) {
        boolean removido = usuarioService.removerUsuario(id);
        if (removido) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
