package com.entendomeudia.service;

import com.entendomeudia.model.Usuario;
import java.util.*;

public class UsuarioService {
    private final Map<String, Usuario> usuarios = new HashMap<>();

    public void incluirUsuario(Usuario usuario) {
        usuarios.put(usuario.getId(), usuario);
    }

    public Collection<Usuario> recuperarUsuarios() {
        return usuarios.values();
    }

    public Usuario buscarPorId(String id) {
        return usuarios.get(id);
    }
}
