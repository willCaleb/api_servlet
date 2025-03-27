package org.will.repository;

import org.will.model.entity.AbstractEntity;

import java.util.List;

public interface AbstractRepository<ENTITY extends AbstractEntity>  {
    ENTITY findById(Integer id);

    ENTITY save(ENTITY entity);

    List<ENTITY> findAll();

    void delete(Integer id);
}
