package org.example.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "endereco_con")
public class Address extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id_endereco")
    @SequenceGenerator(name = "id_endereco", sequenceName = "gen_id_endereco", allocationSize = 1)
    private Integer id;

    private String rua;

    private String numero;

    private String complemento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id")
    private People people;

}
