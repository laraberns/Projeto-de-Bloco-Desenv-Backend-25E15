package com.entendomeudia.testes.loader;

import com.entendomeudia.loader.ConfiguracaoAcessibilidadeLoader;
import com.entendomeudia.loader.UsuarioLoader;
import com.entendomeudia.service.ConfiguracaoAcessibilidadeService;
import com.entendomeudia.service.UsuarioService;

public class TesteConfiguracaoAcessibilidadeLoader {
    public static void main(String[] args) {
        try {
            // Serviços
            UsuarioService usuarioService = new UsuarioService();
            ConfiguracaoAcessibilidadeService configService = new ConfiguracaoAcessibilidadeService();

            // Carrega usuários (necessário antes da config)
            UsuarioLoader usuarioLoader = new UsuarioLoader(usuarioService);
            usuarioLoader.carregarUsuarios("src/main/resources/dados/usuarios.txt");

            // Carrega configurações de acessibilidade
            ConfiguracaoAcessibilidadeLoader configLoader = new ConfiguracaoAcessibilidadeLoader(configService, usuarioService);
            configLoader.carregarConfiguracoes("src/main/resources/dados/acessibilidade.txt");

            // Exibir resultados
            System.out.println("🛠️ Configurações de Acessibilidade carregadas:");
            configService.recuperarConfiguracoes().forEach(System.out::println);

        } catch (Exception e) {
            System.err.println("Erro ao carregar configurações: " + e.getMessage());
        }
    }
}
