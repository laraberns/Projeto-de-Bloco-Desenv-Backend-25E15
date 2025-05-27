package com.entendomeudia.repository;

import com.entendomeudia.model.Rotina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RotinaRepository extends JpaRepository<Rotina, Long> {
}
