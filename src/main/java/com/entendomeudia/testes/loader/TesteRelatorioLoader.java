package com.entendomeudia.testes.loader;

import com.entendomeudia.loader.RelatorioLoader;
import com.entendomeudia.loader.UsuarioLoader;
import com.entendomeudia.service.RelatorioService;
import com.entendomeudia.service.UsuarioService;

public class TesteRelatorioLoader {
    public static void main(String[] args) {
        try {
            // Serviços necessários
            UsuarioService usuarioService = new UsuarioService();
            RelatorioService relatorioService = new RelatorioService();

            // Loader de usuários (deve ser carregado primeiro)
            UsuarioLoader usuarioLoader = new UsuarioLoader(usuarioService);
            usuarioLoader.carregarUsuarios("src/main/resources/dados/usuarios.txt");

            // Loader de relatórios
            RelatorioLoader relatorioLoader = new RelatorioLoader(relatorioService, usuarioService);
            relatorioLoader.carregarRelatorios("src/main/resources/dados/relatorios.txt");

            // Exibição
            System.out.println("📊 Relatórios carregados:");
            relatorioService.recuperarRelatorios().forEach(System.out::println);

        } catch (Exception e) {
            System.err.println("Erro ao carregar relatórios: " + e.getMessage());
        }
    }
}

