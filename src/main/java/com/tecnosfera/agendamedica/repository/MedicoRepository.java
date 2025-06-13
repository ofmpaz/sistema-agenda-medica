package com.tecnosfera.agendamedica.repository;

import com.tecnosfera.agendamedica.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicoRepository extends JpaRepository<Medico,Long> {
}
