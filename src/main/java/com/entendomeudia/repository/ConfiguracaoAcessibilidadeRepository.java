package com.entendomeudia.repository;

import com.entendomeudia.model.ConfiguracaoAcessibilidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracaoAcessibilidadeRepository extends JpaRepository<ConfiguracaoAcessibilidade, Long> {
    ConfiguracaoAcessibilidade findByUsuarioId(Long usuarioId);
}
