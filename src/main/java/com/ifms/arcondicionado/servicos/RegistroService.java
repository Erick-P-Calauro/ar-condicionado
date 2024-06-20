package com.ifms.arcondicionado.servicos;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ifms.arcondicionado.modelos.Registro;
import com.ifms.arcondicionado.repositorios.RegistroRep;

@Service
public class RegistroService {
    
    @Autowired
    RegistroRep repositorio;

    private void salvar(Registro registro) {
        long numeroIdentificador = repositorio.count() + 1L;
        
        repositorio.save(new Registro(numeroIdentificador, registro.getNome(), registro.getConteudo(), registro.getDataRegistro()));
    }

    public void adicionarRegistroCadastro(String modelo, String conteudo){
        Registro registro = new Registro("Cadastro - " + modelo, conteudo, new Date().toString());

        this.salvar(registro);
    }

    public void adicionarRegistroUpdate(String modelo, String preUpdate, String posUpdate){
        Registro registro = new Registro("Update - " + modelo, "BEFORE : " + preUpdate + "AFTER : " + posUpdate, new Date().toString());

        this.salvar(registro);
    }

    public void adicionarRegistroDelete(String modelo,  String conteudo){
        Registro registro = new Registro("Delete - " + modelo, conteudo, new Date().toString());

        this.salvar(registro);
    }

    public void adicionarRegistroComando(String microcontrolador, int responseCode , String comando) {
        Registro registro = new Registro("Comando - " + microcontrolador, "Response Code : " + responseCode + " / Comando : " + comando, new Date().toString());

        this.salvar(registro);
    }

}
