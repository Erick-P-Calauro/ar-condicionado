package com.ifms.arcondicionado.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ifms.arcondicionado.modelos.Equipamento;
import com.ifms.arcondicionado.repositorios.EquipamentoRep;

@Service
public class EquipamentoService {

	@Autowired
	EquipamentoRep repositorio;
	
	// Por padrão já ordenado por bloco e sala
	public List<Equipamento> buscarEquipamentos() {
		return repositorio.findAll(Sort.by("sala.bloco", "sala.nome"));
	}
	
	public Equipamento salvarEquipamento(Equipamento equipamento) {
		return repositorio.save(equipamento);
	}
	
	public Equipamento buscarEquipamento(Long id) {
		Optional<Equipamento> equipamento = repositorio.findById(id);
        if(!equipamento.isPresent()) {
            throw new IllegalArgumentException("Equipamento Inválido"+ id);
        }else {
            return equipamento.get();
        }
	}
	
	public Equipamento buscarEquipamentoMac(String mac) {
		Optional<Equipamento> equipamento = repositorio.findByMacaddr(mac);
        if(!equipamento.isPresent()) {
            return null;
        }else {
            return equipamento.get();
        }
	}
	
	public void deletarEquipamento(Long id) {
		Equipamento e = buscarEquipamento(id);
        repositorio.delete(e);
	}
	
}
