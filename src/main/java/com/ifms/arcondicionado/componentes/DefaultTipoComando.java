package com.ifms.arcondicionado.componentes;

import java.util.ArrayList;
import java.util.List;

public class DefaultTipoComando {
	enum tipoComando {
		L20,L21,L22,L23,L24,L25,ON,OFF;
	}
	
	public List<String> getTiposComando() {
		ArrayList<String> tipoList = new ArrayList<String>();
		
		for(tipoComando t : tipoComando.values()) {
			tipoList.add(t.toString());
		}
		
		return tipoList;
	}
}
