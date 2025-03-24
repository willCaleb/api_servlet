package org.example.repository;

import org.example.model.entity.AbstractEntity;
import org.example.hibernate.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.Table;
import javax.transaction.Transactional;

public class AbstractRepository<ENTITY extends AbstractEntity> {

    private final Class<ENTITY> entityClass;

    public AbstractRepository(Class<ENTITY> entityClass) {
        this.entityClass = entityClass;
    }

    public ENTITY save(ENTITY entity) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction transaction = session.beginTransaction();

        Object saved = session.merge(entity);

        transaction.commit();

        session.close();

        return entityClass.cast(saved);
    }


    @Transactional
    public ENTITY findById(Integer id) {
        String tableName = getTableName();
        String sql = "select * from " + tableName + " where id = " + id;

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query<ENTITY> query = session.createNativeQuery(sql, entityClass);
        ENTITY singleResult = query.getSingleResult();

        session.close();

        initializeLazyCollections(singleResult);

        return singleResult;
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
