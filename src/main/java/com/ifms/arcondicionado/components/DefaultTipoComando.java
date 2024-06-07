package com.ifms.arcondicionado.components;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que armazena de forma estática os tipos de comandos disponíveis
 * para cadastro de comandos no sistema
 * 
 * @since Release 1.0
 * @version 1.0
 * */
public class DefaultTipoComando {
	enum tipoComando {
		L20,L21,L22,L23,L24,L25,OFF;
	}
	
	public List<String> getTiposComando() {
		ArrayList<String> tipoList = new ArrayList<String>();
		
		for(tipoComando t : tipoComando.values()) {
			tipoList.add(t.toString());
		}
		
		return tipoList;
	}
}
