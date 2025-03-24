package org.example.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class PeopleDTO extends AbstractDTO{

    private Integer id;

    private String name;

    private List<AddressDTO> addresses;

}
