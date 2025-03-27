package org.will.repository.impl;

import org.will.model.entity.AbstractEntity;
import org.will.hibernate.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.will.repository.AbstractRepository;

import javax.persistence.Table;
import javax.transaction.Transactional;
import java.util.List;

public class AbstractRepositoryImpl<ENTITY extends AbstractEntity> implements AbstractRepository<ENTITY> {

    private final Class<ENTITY> entityClass;

    public AbstractRepositoryImpl(Class<ENTITY> entityClass) {
        this.entityClass = entityClass;
    }

    @Transactional
    public ENTITY save(ENTITY entity) {

        try {
            Session session = HibernateUtil.getSessionFactory().openSession();

            Transaction transaction = session.beginTransaction();

            Object saved = session.merge(entity);

            transaction.commit();

            session.close();

            return entityClass.cast(saved);
        }catch (Exception e) {
            throw new RuntimeException("Não foi possível incluir os dados.");
        }
    }

    @Transactional
    public ENTITY findById(Integer id) {

        try {
            String tableName = getTableName();
            String sql = "select * from " + tableName + " where id = " + id;

            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            Query<ENTITY> query = session.createNativeQuery(sql, entityClass);

            ENTITY singleResult = query.getSingleResult();

            session.close();

            initializeLazyCollections(singleResult);

            return singleResult;
        } catch (Exception e) {
            throw new RuntimeException("Não foi encontrado registro com o id " + id);
        }
    }

    @Override
    public List<ENTITY> findAll() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        String sql = "SELECT * FROM " + getTableName();

        Query<ENTITY> query = session.createNativeQuery(sql, entityClass);

        List<ENTITY> resultList = query.getResultList();

        session.close();

        return resultList;
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
