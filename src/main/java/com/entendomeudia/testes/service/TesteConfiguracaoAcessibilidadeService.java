package com.entendomeudia.testes.service;

import com.entendomeudia.model.ConfiguracaoAcessibilidade;
import com.entendomeudia.model.Usuario;
import com.entendomeudia.service.ConfiguracaoAcessibilidadeService;

public class TesteConfiguracaoAcessibilidadeService {
    public static void main(String[] args) {
        ConfiguracaoAcessibilidadeService service = new ConfiguracaoAcessibilidadeService();

        // Criar usuários simulados
        Usuario ana = new Usuario("1", "Ana", "principal", "1234");
        Usuario joao = new Usuario("2", "João", "responsavel", "4321");

        // Criar configurações para cada usuário
        ConfiguracaoAcessibilidade confAna = new ConfiguracaoAcessibilidade(ana);
        confAna.setId("conf1");
        confAna.setTamanhoFonte("grande");
        confAna.setContraste(true);
        confAna.setLeituraVoz(true);

        ConfiguracaoAcessibilidade confJoao = new ConfiguracaoAcessibilidade(joao);
        confJoao.setId("conf2");
        confJoao.setTamanhoFonte("medio");
        confJoao.setContraste(false);
        confJoao.setLeituraVoz(false);

        // Inserir no serviço
        service.incluirConfiguracao(confAna);
        service.incluirConfiguracao(confJoao);

        // Buscar por ID de usuário
        System.out.println("🔍 Configuração para o usuário '1':");
        System.out.println(service.buscarPorUsuarioId("1"));

        // Listar todas
        System.out.println("\n📋 Todas as configurações:");
        service.recuperarConfiguracoes().forEach(System.out::println);
    }
}
