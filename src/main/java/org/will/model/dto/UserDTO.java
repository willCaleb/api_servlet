package org.will.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserDTO extends AbstractDTO{

    private Integer id;

    private String username;

    private String password;

}
