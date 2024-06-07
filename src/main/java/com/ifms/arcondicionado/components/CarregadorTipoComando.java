package com.ifms.arcondicionado.components;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.ifms.arcondicionado.modelos.TipoComando;
import com.ifms.arcondicionado.servicos.TipoComandoService;

import jakarta.transaction.Transactional;

/**
 * <p>Classe responsável por inserir os tipos de comandos no banco de dados</p>
 * <p>@CommandLineRunner : disponibiliza função que é executada antes do resto do sistema.</p>
 * 
 * @since Release 1.0
 * @version 1.0 
 * */
@Component
@Transactional
public class CarregadorTipoComando implements CommandLineRunner {
	
	@Autowired
	private TipoComandoService tcr;
	
	/**
	 * <p>Método principal que é executado antes do resto do sistema</p>
	 * 
	 * <p>Verifica todos os tiposComandos armazenados no java e insere no banco de dados os que ainda não foram inseridos.</p>
	 * */
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
