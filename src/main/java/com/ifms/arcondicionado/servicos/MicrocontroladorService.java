package com.ifms.arcondicionado.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifms.arcondicionado.modelos.Microcontrolador;
import com.ifms.arcondicionado.repositorios.MicrocontroladorRep;

@Service
public class MicrocontroladorService {
	
	@Autowired
	MicrocontroladorRep repositorio;
	
	public Microcontrolador salvarEquipConnect(Microcontrolador endereco) {
		return repositorio.save(endereco);
	}
	
	public List<Microcontrolador> buscarEquipConnects() {
		return repositorio.findAll();
		
	}
	
	public Microcontrolador buscarEquipConnect(String endereco) {
		Optional<Microcontrolador> addr = repositorio.findByMacAddress(endereco);
		if(!addr.isPresent()) {
			return null;
		}else {
			return addr.get();
		}
	}
	
	public void deletarEquipConnect(String endereco) {
		Microcontrolador addr = buscarEquipConnect(endereco);
		repositorio.delete(addr);
	}

}
