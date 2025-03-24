package org.example.model.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "people")
public class People extends AbstractEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id_pessoa")
    @SequenceGenerator(name = "id_pessoa", sequenceName = "gen_id_pessoa", allocationSize = 1)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "people", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Address> addresses;

}