package com.ifms.arcondicionado.servicos;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ifms.arcondicionado.modelos.Comando;
import com.ifms.arcondicionado.modelos.Modelo;
import com.ifms.arcondicionado.repositorios.ComandoRep;
import com.ifms.arcondicionado.servicos.Logger.LogObservator;

@Service
public class ComandoService extends LogObservator<Comando>{
	
	@Autowired
	ComandoRep repositorio;
	
	public List<Comando> buscarComandos() {
		return repositorio.findAll(Sort.by("tipoComando.nome"));
	}
	
	/*public Comando salvarComando(Comando comando) {
		return repositorio.save(comando);
	}*/
	
	public Comando buscarComando(Long id) {
		Optional<Comando> comando = repositorio.findById(id);
        if(!comando.isPresent()) {
            throw new IllegalArgumentException("Comando Inválido"+ id);
        }else {
            return comando.get();
        }
	}
	
	public Comando buscarComandoNome(String nome) {
		Optional<Comando> comando = repositorio.findByNome(nome);
        if(!comando.isPresent()) {
            throw new IllegalArgumentException("Comando Inválido"+ nome);
        }else {
            return comando.get();
        }
	}
	
	public Comando buscarComandoModelo(Modelo modelo) {
		Optional<Comando> comando = repositorio.findByModelo(modelo);
        if(!comando.isPresent()) {
            throw new IllegalArgumentException("Comando Inválido"+ modelo.getNome());
        }else {
            return comando.get();
        }
	}
	
	/*public void deletar(Long id) {
		Comando c = buscarComando(id);
        repositorio.delete(c);
	}*/
	
}
