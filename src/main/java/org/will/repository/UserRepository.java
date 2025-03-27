package org.will.repository;

import org.will.model.entity.User;

import java.util.Optional;

public interface UserRepository extends AbstractRepository<User>{
    Optional<User> findByUsername(String username);

}
