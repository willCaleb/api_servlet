package org.will.converter;

import org.will.model.dto.AbstractDTO;
import org.will.model.entity.AbstractEntity;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class Converter {

    public static <E extends AbstractEntity, DTO extends AbstractDTO> DTO toDto (E entity, Class<? extends AbstractDTO> clazz) {
        return (DTO)new ModelMapper().map(entity, clazz);
    }

    public static <DTO extends AbstractDTO, E extends AbstractEntity> E toEntity(DTO dto, Class<? extends AbstractEntity> clazz) {
        return (E)new ModelMapper().map(dto, clazz);
    }

    public static <E extends AbstractEntity, DTO extends AbstractDTO> List<DTO> toDto(List<E> entityList, Class<? extends AbstractDTO> clazz) {
        List<DTO> dtoList = new ArrayList<>();

        entityList.forEach(e -> dtoList.add(toDto(e, clazz)));

        return dtoList;
    }

}
