package com.ifms.arcondicionado.components;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que armazena de forma estática os blocos disponíveis para o cadastro
 * de salas no sistema
 * 
 * @since Release 1.0
 * @version 1.0
 * */
// M = Modulares
public class DefaultBloco {
	enum blocos {
		A,B,C,D,E,M;
	}
	
	public List<String> getBlocos() {
		ArrayList<String> blocoList = new ArrayList<String>();
		
		for(blocos bloco : blocos.values()) {
			blocoList.add(bloco.toString());
		}
		
		return blocoList;
	}
}
