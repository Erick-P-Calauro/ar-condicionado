package com.ifms.arcondicionado.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ifms.arcondicionado.modelos.Agenda;
import com.ifms.arcondicionado.repositorios.AgendaRep;

@Service
public class AgendaService {
	
	@Autowired
	AgendaRep repositorio;
	
	// Por padrão já ordenado por bloco e sala
	public List<Agenda> buscarAgendas() {
		return repositorio.findAll();
	}
	
	public Agenda salvar(Agenda agenda) {
		return repositorio.save(agenda);
	}
	
	public Agenda buscarAgenda(Long id) {
		Optional<Agenda> agenda = repositorio.findById(id);
        if(!agenda.isPresent()) {
            throw new IllegalArgumentException("Agenda Inválida"+ id);
        }else {
            return agenda.get();
        }
	}
	
	public void deletarAgenda(Long id) {
		Agenda e = buscarAgenda(id);
        repositorio.delete(e);
	}
}
