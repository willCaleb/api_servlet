package org.will.model.entity;


import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "people")
public class Person extends AbstractEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id_pessoa")
    @SequenceGenerator(name = "id_pessoa", sequenceName = "gen_id_pessoa", allocationSize = 1)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Address> addresses;

}