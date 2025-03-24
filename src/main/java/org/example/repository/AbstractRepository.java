package org.example.repository;

import org.example.entity.AbstractEntity;
import org.example.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.Table;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class AbstractRepository<ENTITY extends AbstractEntity> {

    @SuppressWarnings("unchecked")
    public Class<ENTITY> getEntityClass() {
        Type[] genericTypes = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        return (Class<ENTITY>) genericTypes[0];
    }

    public ENTITY save(ENTITY entity) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction transaction = session.beginTransaction();

        Object saved = session.merge(entity);

        transaction.commit();

        session.close();

        return getEntityClass().cast(saved);
    }


    public ENTITY findById(Integer id) {

        String tableName = getTableName();

        String sql = "select * from " + tableName + " where id = " + id;

        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        Query<ENTITY> query = session.createNativeQuery(sql, getEntityClass());

        ENTITY singleResult = query.getSingleResult();

        session.close();

        return singleResult;
    }

    private String getTableName() {

        Class<ENTITY> entityClass = getEntityClass();

        if (entityClass.isAnnotationPresent(Table.class)) {
            return entityClass.getAnnotation(Table.class).name();
        }
        return entityClass.getName().toLowerCase();
    }

}
