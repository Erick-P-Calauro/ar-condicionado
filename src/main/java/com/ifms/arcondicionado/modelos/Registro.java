package com.ifms.arcondicionado.modelos;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;

@Entity
public class Registro {
    
    @Id
    private Long id;

    private String nome;

    @Size(max=2000)
    private String conteudo;

    private String dataRegistro;

    public Registro() {

    }

    public Registro(Long id, String nome, String conteudo, String dataRegistro) {
        this.id = id;
        this.nome = nome;
        this.conteudo = conteudo;
        this.dataRegistro = dataRegistro;
    }

    public Registro(String nome, String conteudo, String dataRegistro) {
        this.nome = nome;
        this.conteudo = conteudo;
        this.dataRegistro = dataRegistro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    
}
