package com.entendomeudia.repository;

import com.entendomeudia.model.Rotina;
import com.entendomeudia.model.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RotinaRepositoryTest {

    @Autowired
    private RotinaRepository rotinaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Salvar e buscar uma Rotina")
    void testSalvarEBuscarRotina() {
        // Criar e salvar usuário
        Usuario usuario = new Usuario("João", "principal", "senha123");
        usuario = usuarioRepository.save(usuario);
        usuarioRepository.flush();

        // Criar data da rotina
        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.MAY, 20);
        Date dataRotina = cal.getTime();

        // Criar e salvar rotina
        Rotina rotina = new Rotina();
        rotina.setData(dataRotina);
        rotina.setUsuario(usuario);
        rotina = rotinaRepository.save(rotina);
        rotinaRepository.flush();

        // Buscar todas rotinas
        List<Rotina> rotinas = rotinaRepository.findAll();

        // Verificar se foi salvo corretamente
        assertThat(rotinas).hasSize(1);
        Rotina encontrada = rotinas.get(0);
        assertThat(encontrada.getUsuario().getId()).isEqualTo(usuario.getId());
        assertThat(encontrada.getData()).isEqualTo(dataRotina);
    }
}
