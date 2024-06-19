package com.ifms.arcondicionado.modelos;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Agenda {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String descricao;
	
	@NotNull
	private Boolean ativo;
	
	@NotNull
	private int hora;
	
	@NotNull
	private int minuto;
	
	// 0 = todos os dias
	private int dia;
	
	// 1 a 7 = domingo a sexta, 0 = todos
	private int diaSemana;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "agenda_equipamento", 
			joinColumns=@JoinColumn(name="agenda_id"),
			inverseJoinColumns=@JoinColumn(name="equipamento_id"))
	private List<Equipamento> equipamentos;
	
	@ManyToOne
	private TipoComando tipoComando;
	
	public Agenda() {
		
	}
	
	public Agenda(Long id, String descricao, @NotNull Boolean ativo, int hora, int minuto, int dia, int diaSemana,
			List<Equipamento> equipamentos, TipoComando comandos) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.ativo = ativo;
		this.hora = hora;
		this.minuto = minuto;
		this.dia = dia;
		this.diaSemana = diaSemana;
		this.equipamentos = equipamentos;
		this.tipoComando = comandos;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	public Boolean getAtivo() {
		return ativo;
	}
	public void setHora(int hora) {
		this.hora = hora;
	}
	public int getHora() {
		return hora;
	}
	public void setMinuto(int minuto) {
		this.minuto = minuto;
	}
	public int getMinuto() {
		return minuto;
	}
	public void setDia(int dia) {
		this.dia = dia;
	}
	public int getDia() {
		return dia;
	}
	public void setDiaSemana(int diaSemana) {
		this.diaSemana = diaSemana;
	}
	public int getDiaSemana() {
		return diaSemana;
	}
	public void setEquipamentos(List<Equipamento> equipamentos) {
		this.equipamentos = equipamentos;
	}
	public List<Equipamento> getEquipamentos() {
		return equipamentos;
	}
	public void setComando(TipoComando comando) {
		this.tipoComando = comando;
	}
	public TipoComando getComando() {
		return tipoComando;
	}
	public void setTipoComando(TipoComando tipoComando) {
		this.tipoComando = tipoComando;
	}
	public TipoComando getTipoComando() {
		return tipoComando;
	}
}
