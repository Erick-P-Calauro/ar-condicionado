package com.ifms.arcondicionado.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifms.arcondicionado.modelos.Microcontrolador;

public interface MicrocontroladorRep extends JpaRepository<Microcontrolador, Long> {
	Optional<Microcontrolador> findByMacAddress(String endereco);

}
