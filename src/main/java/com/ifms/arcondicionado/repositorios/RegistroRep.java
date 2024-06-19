package com.ifms.arcondicionado.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifms.arcondicionado.modelos.Registro;

public interface RegistroRep extends JpaRepository<Registro, Long> {
    
}
