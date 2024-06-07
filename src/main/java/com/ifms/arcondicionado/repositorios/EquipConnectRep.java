package com.ifms.arcondicionado.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifms.arcondicionado.modelos.EquipConnect;

public interface EquipConnectRep extends JpaRepository<EquipConnect, Long> {
	Optional<EquipConnect> findByEndereco(String endereco);

}
