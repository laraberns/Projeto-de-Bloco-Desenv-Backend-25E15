package com.entendomeudia.service;

import com.entendomeudia.model.Usuario;
import com.entendomeudia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
}
