package com.ifms.arcondicionado.repositorios;
import com.ifms.arcondicionado.modelos.Modelo;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ModeloRep extends JpaRepository <Modelo, Long> {
	Optional<Modelo> findById(Modelo modelo);
    Optional<Modelo> findByNome(String nome);
}
