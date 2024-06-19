package com.ifms.arcondicionado.modelos;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Por favor forneça o bloco")
    @Size(max=1)
    private String bloco;

    @NotBlank(message="O nome da sala não pode ser nulo")
    @Size(max=4, message="O nome da sala deve conter 4 caracteres")
    @Column(unique=true)
    private String nome;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL)
    private List<Equipamento> equipamento_sala;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull @Size(max = 1) String getBloco() {
        return bloco;
    }

    public void setBloco(@NotNull @Size(max = 1) String bloco) {
        this.bloco = bloco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Equipamento> getEquipamento_sala() {
        return equipamento_sala;
    }

    public void setEquipamento_sala(List<Equipamento> equipamento_sala) {
        this.equipamento_sala = equipamento_sala;
    }

}
