package com.ifms.arcondicionado.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifms.arcondicionado.modelos.Agenda;
import com.ifms.arcondicionado.repositorios.AgendaRep;
import com.ifms.arcondicionado.servicos.Logger.LogObservator;

@Service
public class AgendaService extends LogObservator<Agenda>{
	
	@Autowired
	AgendaRep repositorio;
	
	// Por padrão já ordenado por bloco e sala
	public List<Agenda> buscarAgendas() {
		return repositorio.findAll();
	}
	
	public Agenda buscarAgenda(Long id) {
		Optional<Agenda> agenda = repositorio.findById(id);
        if(!agenda.isPresent()) {
            throw new IllegalArgumentException("Agenda Inválida"+ id);
        }else {
            return agenda.get();
        }
	}
}
