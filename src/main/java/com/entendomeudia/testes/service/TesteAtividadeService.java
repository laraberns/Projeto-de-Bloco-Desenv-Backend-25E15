package com.entendomeudia.testes.service;

import com.entendomeudia.model.Atividade;
import com.entendomeudia.model.Rotina;
import com.entendomeudia.model.Usuario;
import com.entendomeudia.service.AtividadeService;

import java.util.Date;

public class TesteAtividadeService {
    public static void main(String[] args) {
        AtividadeService service = new AtividadeService();

        // Criar usuário e rotina fictícios
        Usuario usuario = new Usuario("1", "Ana", "principal", "1234");
        Rotina rotina = new Rotina("r1", new Date(), usuario);

        // Criar atividades
        Atividade a1 = new Atividade("a1", "Fisioterapia", "09:00", "10:00");
        a1.setDescricao("Sessão com terapeuta");
        a1.setRotina(rotina);

        Atividade a2 = new Atividade("a2", "Leitura", "11:00", "11:30");
        a2.setDescricao("Livro de histórias");
        a2.setRotina(rotina);

        // Inserir no serviço
        service.incluirAtividade(a1);
        service.incluirAtividade(a2);

        // Buscar individualmente
        System.out.println("🔍 Atividade com ID 'a1':");
        System.out.println(service.buscarPorId("a1"));

        // Listar todas
        System.out.println("\n📋 Todas as atividades:");
        service.obterLista().forEach(System.out::println);
    }
}
