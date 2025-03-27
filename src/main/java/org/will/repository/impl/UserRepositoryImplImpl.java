package org.will.repository.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.will.hibernate.HibernateUtil;
import org.will.model.entity.User;
import org.will.repository.UserRepository;

import java.util.Optional;

public class UserRepositoryImplImpl extends AbstractRepositoryImpl<User> implements UserRepository{

    public UserRepositoryImplImpl(Class<User> userClass) {
        super(userClass);
    }

    @Override
    public Optional<User> findByUsername(String username) {

        String sql = "select * from usuario_servlet where username = :username";;

        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        Query<User> query = session.createNativeQuery(sql, User.class);

        query.setParameter("username", username);

        Optional<User> userOptional;

        try {
             userOptional = Optional.of(query.getSingleResult());
        }catch (Exception ignored) {
            return Optional.empty();
        }

        session.close();

        return userOptional;
    }

}
