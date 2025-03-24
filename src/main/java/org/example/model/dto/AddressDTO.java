package org.example.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.IgnoreField;

@Data
@EqualsAndHashCode(callSuper = false)
public class AddressDTO extends AbstractDTO{

    private Integer id;

    private String streetName;

    private String number;

    private String complementaryInformation;

    @IgnoreField
    private PeopleDTO people;

}
