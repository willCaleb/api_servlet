package org.example.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CityDTO extends AbstractDTO{

    private Integer id;

    private String name;

    private String uf;
}
