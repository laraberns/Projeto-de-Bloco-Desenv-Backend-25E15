package com.entendomeudia.testes.loader;

import com.entendomeudia.loader.RotinaLoader;
import com.entendomeudia.loader.UsuarioLoader;
import com.entendomeudia.service.RotinaService;
import com.entendomeudia.service.UsuarioService;

public class TesteRotinaLoader {
    public static void main(String[] args) {
        try {
            // Inicializa os serviços
            UsuarioService usuarioService = new UsuarioService();
            RotinaService rotinaService = new RotinaService();

            // Carrega os usuários (necessário antes das rotinas)
            UsuarioLoader usuarioLoader = new UsuarioLoader(usuarioService);
            usuarioLoader.carregarUsuarios("src/main/resources/dados/usuarios.txt");

            // Carrega as rotinas
            RotinaLoader rotinaLoader = new RotinaLoader(rotinaService, usuarioService);
            rotinaLoader.carregarRotinas("src/main/resources/dados/rotinas.txt");

            // Exibe as rotinas carregadas
            System.out.println("📆 Rotinas carregadas:");
            rotinaService.recuperarRotinas().forEach(System.out::println);

        } catch (Exception e) {
            System.err.println("Erro ao carregar rotinas: " + e.getMessage());
        }
    }
}
