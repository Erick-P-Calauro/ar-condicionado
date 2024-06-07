package com.ifms.arcondicionado.modelos;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

/**
 * Entidade responsável por armazenar os tipos de comandos registrados
 * <p>Os tipos de comandos são, por exemplo, ligar em 20°C, desligar, ligar em 25°C...</p>
 * 
 * @since Release 1.0
 * @version 1.0
 * */
@Entity
public class TipoComando {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long uid;
	
	@NotNull
	private String nome;
	
	@OneToMany(mappedBy="tipoComando", cascade=CascadeType.ALL)
	private List<Comando> comandos;
	
	@OneToMany(mappedBy="tipoComando", cascade=CascadeType.ALL)
	private List<Agenda> agendas;
	
	public TipoComando() {
		
	}
	
	public TipoComando(String nome) {
		this.nome = nome;
	}
	
	public TipoComando(Long uid, String nome, List<Comando> comandos, List<Agenda> agendas){
		this.uid = uid;
		this.nome = nome;
		this.comandos = comandos;
		this.agendas = agendas;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Comando> getComandos() {
		return comandos;
	}

	public void setComandos(List<Comando> comandos) {
		this.comandos = comandos;
	}

	public List<Agenda> getAgendas() {
		return agendas;
	}

	public void setAgendas(List<Agenda> agendas) {
		this.agendas = agendas;
	}
	
}
