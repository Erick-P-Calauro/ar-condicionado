package com.ifms.arcondicionado.modelos;

import org.springframework.beans.factory.annotation.Value;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

/**
 * Entidade que representa um microcontrolador antes do cadastro no sistema
 * 
 * @since Release 1.0
 * @version 1.0
 * */
@Entity
public class EquipConnect {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String endereco;
	
	@NotNull
	private String ip;
	
	/**
	 * Este atributo representa o sinal de vida do microcontrolador
	 * <p>Os microcontroladores estão programados para enviar um sinal a cada minuto e mudar o status desta entidade</p>
	 * */
	@Value("false")
	private boolean status;
	
	@OneToOne(mappedBy="macaddr")
	private Equipamento equipamento;
	
	
	public EquipConnect(String endereco, String ip, boolean status) {
		this.endereco= endereco;
		this.ip = ip;
		this.status = status;
	}
	
	public EquipConnect() {
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public Equipamento getEquipamento() {
		return equipamento;
	}
	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
}
