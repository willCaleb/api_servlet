package org.example.entity;

import lombok.Getter;
import lombok.Setter;
import org.example.IgnoreField;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "address")
public class Address extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id_endereco")
    @SequenceGenerator(name = "id_endereco", sequenceName = "gen_id_endereco", allocationSize = 1)
    private Integer id;

    private String streetName;

    private String number;

    private String complementaryInformation;

    @IgnoreField
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id")
    private People people;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cidade", referencedColumnName = "id")
    private City city;

}
