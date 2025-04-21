package com.entendomeudia.loader;

import com.entendomeudia.model.*;
import com.entendomeudia.service.*;

import java.io.*;
import java.util.*;

public class ConfiguracaoAcessibilidadeLoader {
    private final ConfiguracaoAcessibilidadeService configService;
    private final UsuarioService usuarioService;

    public ConfiguracaoAcessibilidadeLoader(ConfiguracaoAcessibilidadeService configService, UsuarioService usuarioService) {
        this.configService = configService;
        this.usuarioService = usuarioService;
    }

    public void carregarConfiguracoes(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        Map<String, String> map = new HashMap<>();
        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                configService.incluirConfiguracao(criarConfiguracao(map));
                map.clear();
            } else {
                String[] parts = line.split("=");
                if (parts.length == 2) map.put(parts[0], parts[1]);
            }
        }
        if (!map.isEmpty()) configService.incluirConfiguracao(criarConfiguracao(map));
        br.close();
    }

    private ConfiguracaoAcessibilidade criarConfiguracao(Map<String, String> map) {
        Usuario usuario = usuarioService.buscarPorId(map.get("usuarioId"));
        ConfiguracaoAcessibilidade c = new ConfiguracaoAcessibilidade(usuario);
        c.setId(map.get("id"));
        c.setTamanhoFonte(map.get("tamanhoFonte"));
        c.setContraste(Boolean.parseBoolean(map.get("contraste")));
        c.setLeituraVoz(Boolean.parseBoolean(map.get("leituraVoz")));
        return c;
    }
}
