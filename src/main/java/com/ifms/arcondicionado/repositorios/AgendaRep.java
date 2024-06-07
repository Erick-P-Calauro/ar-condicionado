package com.ifms.arcondicionado.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifms.arcondicionado.modelos.Agenda;

public interface AgendaRep extends JpaRepository<Agenda, Long>{

}
