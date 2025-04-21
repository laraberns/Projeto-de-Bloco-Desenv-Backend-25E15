package com.entendomeudia.testes.service;

import com.entendomeudia.model.Relatorio;
import com.entendomeudia.model.Usuario;
import com.entendomeudia.service.RelatorioService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TesteRelatorioService {
    public static void main(String[] args) throws Exception {
        RelatorioService relatorioService = new RelatorioService();

        // Criar usuário simulado
        Usuario usuario = new Usuario("1", "Ana", "principal", "senha123");

        // Criar relatórios
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date inicio = sdf.parse("2024-10-01");
        Date fim = sdf.parse("2024-10-07");

        Relatorio r1 = new Relatorio(usuario, inicio, fim);
        r1.setId("rel1");
        r1.setTotalAtividades(10);
        r1.setConcluidas(8);
        r1.setNaoRealizadas(2);

        Relatorio r2 = new Relatorio(usuario, sdf.parse("2024-10-08"), sdf.parse("2024-10-14"));
        r2.setId("rel2");
        r2.setTotalAtividades(12);
        r2.setConcluidas(11);
        r2.setNaoRealizadas(1);

        // Inserir no serviço
        relatorioService.incluirRelatorio(r1);
        relatorioService.incluirRelatorio(r2);

        // Buscar por ID
        System.out.println("🔍 Relatório com ID 'rel1':");
        System.out.println(relatorioService.buscarPorId("rel1"));

        // Listar todos
        System.out.println("\n📋 Todos os relatórios:");
        relatorioService.recuperarRelatorios().forEach(System.out::println);
    }
}
