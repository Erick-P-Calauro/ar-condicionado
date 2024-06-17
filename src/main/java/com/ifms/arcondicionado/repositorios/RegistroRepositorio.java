package com.ifms.arcondicionado.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifms.arcondicionado.modelos.Registro;

public interface RegistroRepositorio extends JpaRepository<Registro, Long> {
    
}
