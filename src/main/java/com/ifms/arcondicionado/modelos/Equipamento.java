package com.ifms.arcondicionado.modelos;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Equipamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @OneToOne
    private Microcontrolador microcontrolador;

    @ManyToOne
    @JoinColumn(name="modelo_id")
    private Modelo modelo;
    
    @ManyToOne
    @JoinColumn(name="sala_id")
    private Sala sala;

    @Size(max=30, message="A descrição deve conter no máximo 30 caracteres")
    private String descricao;
    
    @ManyToMany(mappedBy="equipamentos", cascade=CascadeType.ALL)
    private List<Agenda> agendas;
    
    public Equipamento() {
    	
    }
    
    public Equipamento(Microcontrolador mac) {
    	this.microcontrolador = mac;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


	public List<Agenda> getAgendas() {
		return agendas;
	}

	public void setAgendas(List<Agenda> agendas) {
		this.agendas = agendas;
	}

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public Microcontrolador getMicrocontrolador() {
		return microcontrolador;
	}

	public void setMicrocontrolador(Microcontrolador microcontrolador) {
		this.microcontrolador = microcontrolador;
	}
}
