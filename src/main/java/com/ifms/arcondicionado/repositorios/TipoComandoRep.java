package com.ifms.arcondicionado.repositorios;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ifms.arcondicionado.modelos.TipoComando;

public interface TipoComandoRep extends JpaRepository<TipoComando, Long>{
	Optional<TipoComando> findByNome(String nome);
}
