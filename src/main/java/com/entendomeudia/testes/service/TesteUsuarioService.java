package com.entendomeudia.testes.service;

import com.entendomeudia.model.Usuario;
import com.entendomeudia.service.UsuarioService;

public class TesteUsuarioService {
    public static void main(String[] args) {
        UsuarioService usuarioService = new UsuarioService();

        // Criar usuários fictícios
        Usuario u1 = new Usuario("1", "Ana", "principal", "1234");
        u1.setEmail("ana@email.com");
        u1.setTelefone("11999999999");

        Usuario u2 = new Usuario("2", "João", "responsavel", "5678");
        u2.setEmail("joao@email.com");
        u2.setTelefone("11988888888");

        // Incluir no service
        usuarioService.incluirUsuario(u1);
        usuarioService.incluirUsuario(u2);

        // Buscar por ID
        System.out.println("🔍 Buscar por ID 1:");
        System.out.println(usuarioService.buscarPorId("1"));

        // Recuperar todos os usuários
        System.out.println("\n📋 Todos os usuários:");
        usuarioService.recuperarUsuarios().forEach(System.out::println);
    }
}
