package com.ifms.arcondicionado.components;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.ifms.arcondicionado.modelos.TipoComando;
import com.ifms.arcondicionado.servicos.TipoComandoService;

import jakarta.transaction.Transactional;

@Component
@Transactional
public class CarregadorTipoComando implements CommandLineRunner {
	
	@Autowired
	private TipoComandoService tcr;
	
	@Override
	public void run(String... args) throws Exception {
		
		List<String> tipos = tcr.buscarDefault();
		
		for (String tipo : tipos) {
			TipoComando tc = tcr.buscarTipoComandoNome(tipo);
			if (tc == null) {
				tc = new TipoComando(tipo);
				tcr.salvarTipoComando(tc);
			}
		}
	}

}
