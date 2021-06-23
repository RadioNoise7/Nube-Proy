package com.cloud.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cloud.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    
    public User findByUsername(String username);

    public User findByEmail(String email);

}