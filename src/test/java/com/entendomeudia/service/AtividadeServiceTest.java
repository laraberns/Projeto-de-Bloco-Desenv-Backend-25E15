package com.entendomeudia.service;

import com.entendomeudia.model.Atividade;
import com.entendomeudia.repository.AtividadeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Commit // Faz o commit real para persistir os dados no banco
@Transactional // Mantém a transação aberta durante o teste
class AtividadeServiceTest {

    @Autowired
    private AtividadeService atividadeService;

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Test
    void testIncluirListarBuscar() {
        Atividade atividade = new Atividade("Estudar", "09:00", "10:00");
        atividade.setDescricao("Testar serviço");

        // Salvar
        Atividade salvo = atividadeService.incluirAtividade(atividade);
        assertThat(salvo.getId()).isNotNull();

        // Listar todas atividades
        Collection<Atividade> lista = atividadeService.obterLista();
        assertThat(lista).isNotEmpty();
        assertThat(lista).contains(salvo);

        // Buscar por ID
        Atividade buscado = atividadeService.buscarPorId(salvo.getId());
        assertThat(buscado).isNotNull();
        assertThat(buscado.getDescricao()).isEqualTo("Testar serviço");
    }
}
