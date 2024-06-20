package com.ifms.arcondicionado.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ifms.arcondicionado.componentes.DefaultBloco;
import com.ifms.arcondicionado.modelos.Sala;
import com.ifms.arcondicionado.repositorios.SalaRep;
import com.ifms.arcondicionado.servicos.Logger.LogObservator;

@Service
public class SalaService extends LogObservator<Sala>{
	
	@Autowired
	SalaRep repositorio;
	
	public List<Sala> buscarSalas() {
		return repositorio.findAll(Sort.by("bloco", "nome"));
	}
	
	/*public Sala salvarSala(Sala sala) {
		Optional<Sala> s = repositorio.findByNome(sala.getNome());

		if(s.isPresent()) {
			return null;
		}

		return repositorio.save(sala);
	}*/
	
	public Sala buscarSala(Long id) {
		Optional<Sala> sala = repositorio.findById(id);
		if(!sala.isPresent()) {
            throw new IllegalArgumentException("Sala Inválida"+ id);
        }else {
            return sala.get();
        }
	}
	
	// Buscar e deletar
	/*public void deletarSala(Long id) {
		Sala sala = buscarSala(id);
		repositorio.delete(sala);
	}*/
	
	// Deletar o objeto fornecido
	/*public void deletarSala(Sala sala) {
		repositorio.delete(sala);
	}*/
	
	public List<String> buscarBlocos() {
		DefaultBloco df = new DefaultBloco();
		return df.getBlocos();
	}
	
}
