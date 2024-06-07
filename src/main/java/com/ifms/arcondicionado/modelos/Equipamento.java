package com.ifms.arcondicionado.modelos;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Entidade que representa os microcontroladores após o cadastro no sistema
 * 
 * @since Release 1.0
 * @version 1.0
 * */
@Entity
public class Equipamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @OneToOne
    private EquipConnect macaddr;

    /**
     * Cada microcontrolador representa apenas um equipamento que por sua vez tem apenas um modelo
     * */
    @ManyToOne
    @JoinColumn(name="modelo_id")
    private Modelo modelo;
    
    /**
     * Cada microcontrolador tem sua área de ação em apenas uma sala
     * */
    @ManyToOne
    @JoinColumn(name="sala_id")
    private Sala sala;

    @Size(max=30, message="A descrição deve conter no máximo 30 caracteres")
    private String descricao;
    
    /**
     * Um microcontrolador pode estar sujeito a vários agendamentos de comandoss
     * */
    @ManyToMany(mappedBy="equipamentos", cascade=CascadeType.ALL)
    private List<Agenda> agendas;
    
    public Equipamento() {
    	
    }
    
    public Equipamento(EquipConnect mac) {
    	this.macaddr = mac;
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
    
    public EquipConnect getMacaddr() {
		return macaddr;
	}

	public void setMacaddr(EquipConnect macaddr) {
		this.macaddr = macaddr;
	}
}
