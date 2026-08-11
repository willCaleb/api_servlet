package org.will.hibernate;

import org.will.ServletApi;
import org.will.model.entity.AbstractEntity;
import org.will.model.entity.Person;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.reflections.Reflections;

import jakarta.persistence.Entity;
import org.will.servlets.AbstractServlet;

import java.util.List;
import java.util.Set;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();

            MetadataSources metadataSources = new MetadataSources(registry);

            Reflections reflections = new Reflections(ServletApi.class.getPackage().getName());

            Set<Class<? extends AbstractEntity>> entityClasses = reflections.getSubTypesOf(AbstractEntity.class);

            entityClasses.forEach(metadataSources::addAnnotatedClass);

            return metadataSources.buildMetadata().buildSessionFactory();

        } catch (Exception ex) {
            throw new ExceptionInInitializerError("Falha ao inicializar o Hibernate: " + ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
