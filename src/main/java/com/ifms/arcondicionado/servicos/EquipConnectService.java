package com.ifms.arcondicionado.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifms.arcondicionado.modelos.EquipConnect;
import com.ifms.arcondicionado.repositorios.EquipConnectRep;

@Service
public class EquipConnectService {
	
	@Autowired
	EquipConnectRep repositorio;
	
	public EquipConnect salvarEquipConnect(EquipConnect endereco) {
		return repositorio.save(endereco);
	}
	
	public List<EquipConnect> buscarEquipConnects() {
		return repositorio.findAll();
		
	}
	
	public EquipConnect buscarEquipConnect(String endereco) {
		Optional<EquipConnect> addr = repositorio.findByEndereco(endereco);
		if(!addr.isPresent()) {
			return null;
		}else {
			return addr.get();
		}
	}
	
	public void deletarEquipConnect(String endereco) {
		EquipConnect addr = buscarEquipConnect(endereco);
		repositorio.delete(addr);
	}

}
