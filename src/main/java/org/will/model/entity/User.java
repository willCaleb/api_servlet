package org.will.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "usuario_servlet")
public class User extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id_usuario")
    @SequenceGenerator(name = "id_usuario", sequenceName = "gen_id_usuario", allocationSize = 1)
    private Integer id;

    private String username;

    private String password;

}
