package com.entendomeudia.testes.service;

import com.entendomeudia.model.Rotina;
import com.entendomeudia.model.Usuario;
import com.entendomeudia.service.RotinaService;

import java.text.SimpleDateFormat;

public class TesteRotinaService {
    public static void main(String[] args) throws Exception {
        RotinaService rotinaService = new RotinaService();

        // Criar usuário fictício associado à rotina
        Usuario usuario = new Usuario("1", "Ana", "principal", "1234");

        // Criar rotinas
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Rotina r1 = new Rotina("r1", sdf.parse("2024-10-01"), usuario);
        Rotina r2 = new Rotina("r2", sdf.parse("2024-10-02"), usuario);

        // Incluir no service
        rotinaService.incluirRotina(r1);
        rotinaService.incluirRotina(r2);

        // Buscar por ID
        System.out.println("🔍 Buscar por ID 'r1':");
        System.out.println(rotinaService.buscarPorId("r1"));

        // Recuperar todas as rotinas
        System.out.println("\n📋 Todas as rotinas:");
        rotinaService.recuperarRotinas().forEach(System.out::println);
    }
}
