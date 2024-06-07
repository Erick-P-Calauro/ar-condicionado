package com.ifms.arcondicionado.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ifms.arcondicionado.modelos.Comando;
import com.ifms.arcondicionado.modelos.Modelo;

import java.util.Optional;

public interface ComandoRep extends JpaRepository<Comando, Long> {
    Optional<Comando> findById(Long id);
    Optional<Comando> findByNome(String nome);
    Optional<Comando> findByModelo(Modelo modelo);
}
