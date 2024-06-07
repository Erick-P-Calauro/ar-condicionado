package com.ifms.arcondicionado.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Entidade responsável por armazenar um comando
 * 
 * <p>Cada comando serve para um modelo e pode ser hexadecimal, raw ou os dois.</p>
 * 
 * @since Release 1.0
 * @version 1.0
 * */
@Entity
public class Comando {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Size(max=20, message="O nome deve conter até 20 caracteres")
	@NotBlank(message="O nome não pode ser nulo.")
    private String nome;

    /**
     * Cada comando corresponde a apenas um tipo de comando (Ex : L20 - Ligar com a temperatura de 20°C)
     * <p>Olhar os tipos de comandos disponíveis na classe DefaultTipoComando</p>
     * */
    @ManyToOne
    private TipoComando tipoComando;

    @Size(max=1500, message="O comando raw deve conter no máximo 1500 caracteres.")
    private String raw;
    
	@Size(max=49, message="O comando hexadecimal deve conter no máximo 49 caracteres.")
    private String hexadecimal;
	
	/**
	 * Cada comando corresponde a apenas um modelo pois não há variação de comandos
	 * entre equipamentos de mesmo modelo.
	 * */
    @ManyToOne
    @JoinColumn(name="modelo_id")
    private Modelo modelo;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	public void setRaw(String raw) {
		this.raw = raw;
	}
	public String getRaw() {
		return raw;
	}
	public void setHexadecimal(String hexadecimal) {
		this.hexadecimal = hexadecimal;
	}
	public String getHexadecimal() {
		return hexadecimal;
	}
	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}
	public Modelo getModelo() {
		return modelo;
	}
	public void setTipoComando(TipoComando tipoComando) {
		this.tipoComando = tipoComando;
	}
	public TipoComando getTipoComando() {
		return tipoComando;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNome() {
		return nome;
	}
}
