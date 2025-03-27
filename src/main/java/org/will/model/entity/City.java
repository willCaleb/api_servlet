package org.will.model.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "city")
public class City extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id_cidade")
    @SequenceGenerator(name = "id_cidade", sequenceName = "gen_id_cidade", allocationSize = 1)
    private Integer id;

    private String name;

    private String uf;

}
