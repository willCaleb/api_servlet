package org.will.model.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import org.will.auth.Role;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

}
