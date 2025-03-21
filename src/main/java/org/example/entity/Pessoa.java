package org.example.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "teste_teste")
public class Pessoa extends AbstractEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id_pessoa")
    @SequenceGenerator(name = "id_pessoa", sequenceName = "gen_id_pessoa", allocationSize = 1)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @OneToMany(mappedBy = "pessoa", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Endereco> enderecos;

}