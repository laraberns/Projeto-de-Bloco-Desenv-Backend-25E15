package com.entendomeudia.testes.loader;

import com.entendomeudia.loader.UsuarioLoader;
import com.entendomeudia.service.UsuarioService;

public class TesteUsuarioLoader {
    public static void main(String[] args) {
        try {
            // Instancia o service
            UsuarioService usuarioService = new UsuarioService();

            // Instancia o loader com injeção do service
            UsuarioLoader loader = new UsuarioLoader(usuarioService);

            // Caminho do arquivo com múltiplos usuários
            String caminhoArquivo = "src/main/resources/dados/usuarios.txt";

            // Carrega os usuários
            loader.carregarUsuarios(caminhoArquivo);

            // Exibe os usuários carregados
            System.out.println("👥 Usuários carregados:");
            usuarioService.recuperarUsuarios().forEach(System.out::println);

        } catch (Exception e) {
            System.err.println("Erro ao carregar usuários: " + e.getMessage());
        }
    }
}
