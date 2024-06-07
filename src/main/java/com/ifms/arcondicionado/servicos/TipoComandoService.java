package com.ifms.arcondicionado.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ifms.arcondicionado.components.DefaultTipoComando;
import com.ifms.arcondicionado.modelos.TipoComando;
import com.ifms.arcondicionado.repositorios.TipoComandoRep;

@Service
public class TipoComandoService {
	
	@Autowired
	TipoComandoRep repositorio;
	
	public List<TipoComando> buscarTiposComando() {
		return repositorio.findAll();
	}
	
	public TipoComando salvarTipoComando(TipoComando tipoComando) {
		return repositorio.save(tipoComando);
	}
	
	public TipoComando buscarTipoComando(Long id) {
		Optional<TipoComando> tipoComando = repositorio.findById(id);
		if(!tipoComando.isPresent()) {
            throw new IllegalArgumentException("Tipo Comando Inválido "+ id);
        }else {
            return tipoComando.get();
        }
	}
	
	public TipoComando buscarTipoComandoNome(String nome) {
		Optional<TipoComando> tipoComando = repositorio.findByNome(nome);
		if(!tipoComando.isPresent()) {
            return null;
        }else {
            return tipoComando.get();
        }
	}
	
	// Buscar e deletar
	public void deletarSala(Long id) {
		TipoComando tipoComando = buscarTipoComando(id);
		repositorio.delete(tipoComando);
	}
	
	public List<String> buscarDefault() {
		DefaultTipoComando dtc = new DefaultTipoComando();
		return dtc.getTiposComando();
	}
	
}	
