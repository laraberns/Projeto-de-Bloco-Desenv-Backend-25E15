package com.entendomeudia.testes.loader;

import com.entendomeudia.loader.AtividadeLoader;
import com.entendomeudia.loader.RotinaLoader;
import com.entendomeudia.loader.UsuarioLoader;
import com.entendomeudia.model.Atividade;
import com.entendomeudia.service.AtividadeService;
import com.entendomeudia.service.RotinaService;
import com.entendomeudia.service.UsuarioService;

public class TesteAtividadeLoader {
    public static void main(String[] args) {
        try {
            // Instancia serviços
            UsuarioService usuarioService = new UsuarioService();
            RotinaService rotinaService = new RotinaService();
            AtividadeService atividadeService = new AtividadeService();

            // Carrega usuários e rotinas antes das atividades
            UsuarioLoader usuarioLoader = new UsuarioLoader(usuarioService);
            usuarioLoader.carregarUsuarios("src/main/resources/dados/usuarios.txt");

            RotinaLoader rotinaLoader = new RotinaLoader(rotinaService, usuarioService);
            rotinaLoader.carregarRotinas("src/main/resources/dados/rotinas.txt");

            // Carrega atividades
            AtividadeLoader atividadeLoader = new AtividadeLoader(atividadeService, rotinaService);
            atividadeLoader.carregarAtividades("src/main/resources/dados/atividades.txt");

            // Exibe resultados
            System.out.println("✅ Atividades carregadas:");
            for (Atividade atividade : atividadeService.obterLista()) {
                System.out.println(atividade);
            }

        } catch (Exception e) {
            System.err.println("Erro ao carregar atividades: " + e.getMessage());
        }
    }
}
