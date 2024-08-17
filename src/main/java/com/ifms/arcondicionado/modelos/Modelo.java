package com.ifms.arcondicionado.modelos;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
public class Modelo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="O nome do modelo não pode ser nulo")
    @Size(max=20, message="O nome do modelo deve conter até 20 caracteres")
    @Column(unique=true)
    private String nome;

    @OneToMany(mappedBy = "modelo", cascade = CascadeType.ALL)
    private List<Equipamento> equipamento_modelo;

    @OneToMany(mappedBy = "modelo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Comando> comando;

    public Long getId() {
        return id;
    }

    public List<Comando> getComando() {
        /*Collections.sort(comando, new java.util.Comparator<Comando>() {
            @Override
            public int compare(Comando  c1, Comando  c2){
                return c1.getTipoComando().getNome().compareTo(c2.getTipoComando().getNome());
            }
        });*/
        
        return comando;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Equipamento> getEquipamento_modelo() {
        return equipamento_modelo;
    }

    public void setEquipamento_modelo(List<Equipamento> equipamento_modelo) {
        this.equipamento_modelo = equipamento_modelo;
    }

    public void setComando(List<Comando> comando) {
        this.comando = comando;
    }

}
