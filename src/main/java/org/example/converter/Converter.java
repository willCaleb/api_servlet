package org.example.converter;

import org.example.model.dto.AbstractDTO;
import org.example.model.entity.AbstractEntity;
import org.modelmapper.ModelMapper;

@SuppressWarnings("unchecked")
public class Converter {

    public static <E extends AbstractEntity, DTO extends AbstractDTO> DTO toDto (E entity, Class<? extends AbstractDTO> clazz) {
        return (DTO)new ModelMapper().map(entity, clazz);
    }

    public static <DTO extends AbstractDTO, E extends AbstractEntity> E toEntity(DTO dto, Class<? extends AbstractEntity> clazz) {
        return (E)new ModelMapper().map(dto, clazz);
    }

}
