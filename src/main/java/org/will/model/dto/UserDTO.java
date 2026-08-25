package org.will.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.will.auth.Role;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserDTO extends AbstractDTO{

    private Integer id;

    private String username;

    private String password;

    private Role role;

}
