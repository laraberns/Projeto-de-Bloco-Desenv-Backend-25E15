package com.entendomeudia.service;

import com.entendomeudia.model.Usuario;
import com.entendomeudia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public Usuario incluirUsuario(Usuario usuario) {
        return repository.save(usuario);
    }

    public Collection<Usuario> recuperarUsuarios() {
        return repository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Usuario atualizarUsuario(Long id, Usuario novoUsuario) {
        Optional<Usuario> existente = repository.findById(id);
        if (existente.isPresent()) {
            Usuario usuarioAtual = existente.get();
            // Atualiza os campos, mas não o id
            usuarioAtual.setNome(novoUsuario.getNome());
            usuarioAtual.setTipo(novoUsuario.getTipo());
            usuarioAtual.setEmail(novoUsuario.getEmail());
            usuarioAtual.setTelefone(novoUsuario.getTelefone());
            usuarioAtual.setSenha(novoUsuario.getSenha());
            // Se quiser atualizar rotinas, configuracoes ou relatorios, deve tratar separadamente
            return repository.save(usuarioAtual);
        }
        return null;
    }

    public boolean removerUsuario(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
