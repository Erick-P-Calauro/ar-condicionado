package com.ifms.arcondicionado.repositorios;

import com.ifms.arcondicionado.modelos.Equipamento;
import com.ifms.arcondicionado.modelos.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EquipamentoRep extends JpaRepository<Equipamento, Long> {	
	List<Equipamento> findBySala(Sala sala);
	Optional<Equipamento> findByMicrocontrolador(String macAddress);
}
