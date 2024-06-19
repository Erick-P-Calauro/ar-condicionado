package com.ifms.arcondicionado.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ifms.arcondicionado.modelos.Registro;
import com.ifms.arcondicionado.repositorios.RegistroRep;

@Service
public class RegistroService {
    
    @Autowired
    RegistroRep repositorio;

    public void salvar(Registro registro) {
        long entidades = repositorio.count();
        entidades += 1L;
        repositorio.save(new Registro(entidades, registro.getEquipamento(), registro.getConteudo(), registro.getDataRegistro()));
    }

}
