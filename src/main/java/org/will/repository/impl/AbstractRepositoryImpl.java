package org.will.repository.impl;

import org.will.exception.CustomException;
import org.will.model.EnumException;
import org.will.model.entity.AbstractEntity;
import org.will.hibernate.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.will.repository.AbstractRepository;

import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import java.util.List;

public class AbstractRepositoryImpl<ENTITY extends AbstractEntity> implements AbstractRepository<ENTITY> {

    private final Class<ENTITY> entityClass;

    public AbstractRepositoryImpl(Class<ENTITY> entityClass) {
        this.entityClass = entityClass;
    }

    @Transactional
    public ENTITY save(ENTITY entity) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()){

            Transaction transaction = session.beginTransaction();

            Object saved = session.merge(entity);

            transaction.commit();

            return entityClass.cast(saved);
        }catch (Exception e) {
            throw new CustomException(EnumException.SAVE_ERROR);
        }
    }

    @Transactional
    public ENTITY findById(Integer id) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            String sql = "select * from " + getTableName() + " where id = " + id;

            session.beginTransaction();

            Query<ENTITY> query = session.createNativeQuery(sql, entityClass);

            ENTITY singleResult = query.getSingleResult();

            initializeLazyCollections(singleResult);

            return singleResult;
        } catch (Exception e) {
            throw new CustomException("Não foi encontrado registro de " + entityClass.getSimpleName() + " com o id " + id);
        }
    }

    @Override
    public List<ENTITY> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            session.beginTransaction();

            String sql = "SELECT * FROM " + getTableName();

            Query<ENTITY> query = session.createNativeQuery(sql, entityClass);

            List<ENTITY> resultList = query.getResultList();

            session.close();

            return resultList;
        }catch (Exception ex) {
            throw new CustomException("Falha ao retornar lista de " + entityClass.getSimpleName());
        }
    }

    @Override
    public void delete(Integer id) {

    }

    private void initializeLazyCollections(ENTITY entity) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.merge(entity);
            Hibernate.initialize(entity);
            session.getTransaction().commit();
        }
    }

    private String getTableName() {

        if (entityClass.isAnnotationPresent(Table.class)) {
            return entityClass.getAnnotation(Table.class).name();
        }
        return entityClass.getName().toLowerCase();
    }

}
