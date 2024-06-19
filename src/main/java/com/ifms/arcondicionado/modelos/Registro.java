package com.ifms.arcondicionado.modelos;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;

@Entity
public class Registro {
    
    @Id
    private Long id;

    @ManyToOne
    private Microcontrolador equipamento;

    @Size(max=3000)
    private String conteudo;

    private String dataRegistro;

    public Registro() {

    }

    public Registro(Long id, Microcontrolador equipamento, String conteudo, String dataRegistro) {
        this.id = id;
        this.equipamento = equipamento;
        this.conteudo = conteudo;
        this.dataRegistro = dataRegistro;
    }

    public Registro(Microcontrolador equipamento, String conteudo, String dataRegistro) {
        this.equipamento = equipamento;
        this.conteudo = conteudo;
        this.dataRegistro = dataRegistro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Microcontrolador getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Microcontrolador equipamento) {
        this.equipamento = equipamento;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(String dataRegistro) {
        this.dataRegistro = dataRegistro;
    }
}
