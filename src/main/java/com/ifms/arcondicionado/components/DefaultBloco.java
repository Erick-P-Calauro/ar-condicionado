package com.ifms.arcondicionado.components;

import java.util.ArrayList;
import java.util.List;

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
