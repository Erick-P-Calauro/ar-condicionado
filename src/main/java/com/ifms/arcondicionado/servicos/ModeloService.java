package com.ifms.arcondicionado.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.ifms.arcondicionado.modelos.Modelo;
import com.ifms.arcondicionado.repositorios.ModeloRep;

@Service
public class ModeloService {
	
	@Autowired
	ModeloRep repositorio;
	
	public List<Modelo> buscarModelos() {
		return repositorio.findAll(Sort.by("nome"));
	}
	
	public Modelo salvarModelo(Modelo modelo) {
		Optional<Modelo> m = repositorio.findByNome(modelo.getNome());

		if(m.isPresent()) {
			return null;
		}
		
		return repositorio.save(modelo);
	}
	
	public Modelo buscarModelo(Long id) {
		Optional<Modelo> modelo = repositorio.findById(id);
        if(!modelo.isPresent()) {
            throw new IllegalArgumentException("Modelo Inválido"+ id);
        }else {
            return modelo.get();
        }
	}
	
	public Modelo buscarModeloNome(String nome) {
		Optional<Modelo> modelo = repositorio.findByNome(nome);
        if(!modelo.isPresent()) {
            throw new IllegalArgumentException("Modelo Inválido"+ nome);
        }else {
            return modelo.get();
        }
	}
	
	public void deletarModelo(Long id) {
		Modelo modelo = buscarModelo(id);
		repositorio.delete(modelo);
	}
	
	public void deletarModelo(Modelo modelo) {
		repositorio.delete(modelo);
	}
	
}
