package com.ifms.arcondicionado.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ifms.arcondicionado.modelos.Modelo;
import com.ifms.arcondicionado.repositorios.ModeloRep;
import com.ifms.arcondicionado.servicos.Logger.ContentExtractor;
import com.ifms.arcondicionado.servicos.Logger.LogObservator;

@Service
public class ModeloService extends LogObservator<Modelo>{
	
	@Autowired
	ModeloRep repositorio;

	@Autowired
	RegistroService logger;

	@Autowired
	ContentExtractor<Modelo> extractor;
	
	public List<Modelo> buscarModelos() {
		return repositorio.findAll(Sort.by("nome"));
	}
	
	@Override
	public Modelo salvar(Modelo modelo) {
		Optional<Modelo> m = repositorio.findByNome(modelo.getNome());

		if(m.isPresent()) {
			return null;
		}
		
		Modelo novoModelo = repositorio.save(modelo);
		logger.adicionarRegistroCadastro("Modelo", extractor.extract(novoModelo));
		return novoModelo;
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
}
