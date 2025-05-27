package com.entendomeudia.repository;

import com.entendomeudia.model.Atividade;
import com.entendomeudia.model.Usuario;
import com.entendomeudia.model.Rotina;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AtividadeRepositoryTest {

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RotinaRepository rotinaRepository;

    @Test
    @DisplayName("Salvar e buscar uma Atividade")
    void testSalvarEBuscarAtividade() {
        Usuario usuario = new Usuario("Maria", "principal", "senha123");
        usuario.setEmail("maria@email.com");
        usuario.setTelefone("1111-1111");
        usuario = usuarioRepository.save(usuario);
        usuarioRepository.flush();

        Rotina rotina = new Rotina(new Date(), usuario);
        rotina = rotinaRepository.save(rotina);
        rotinaRepository.flush();

        Atividade atividade = new Atividade();
        atividade.setDescricao("Tomar remédio");
        atividade.setHorarioInicio("08:00");
        atividade.setHorarioFim("08:30");
        atividade.setStatus("pendente");
        atividade.setRotina(rotina);

        atividade = atividadeRepository.save(atividade);
        atividadeRepository.flush();

        // Asserts para garantir persistência e id gerado
        assertThat(atividade.getId()).isNotNull();

        List<Atividade> todas = atividadeRepository.findAll();
        assertThat(todas).hasSize(1);

        Atividade salva = todas.get(0);
        assertThat(salva.getDescricao()).isEqualTo("Tomar remédio");
        assertThat(salva.getRotina()).isNotNull();
        assertThat(salva.getRotina().getId()).isEqualTo(rotina.getId());
        assertThat(salva.getHorarioInicio()).isEqualTo("08:00");
        assertThat(salva.getStatus()).isEqualTo("pendente");
    }

    @Test
    @DisplayName("Deletar uma Atividade")
    void testDeletarAtividade() {
        Usuario usuario = new Usuario("Carlos", "principal", "abc123");
        usuario = usuarioRepository.save(usuario);
        usuarioRepository.flush();

        Rotina rotina = new Rotina(new Date(), usuario);
        rotina = rotinaRepository.save(rotina);
        rotinaRepository.flush();

        Atividade atividade = new Atividade();
        atividade.setDescricao("Exercício");
        atividade.setHorarioInicio("10:00");
        atividade.setHorarioFim("11:00");
        atividade.setStatus("pendente");
        atividade.setRotina(rotina);

        Atividade salva = atividadeRepository.save(atividade);
        atividadeRepository.flush();

        // Agora deletar
        atividadeRepository.delete(salva);
        atividadeRepository.flush();

        List<Atividade> todas = atividadeRepository.findAll();
        assertThat(todas).isEmpty();
    }
}
