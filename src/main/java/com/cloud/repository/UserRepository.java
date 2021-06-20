package com.cloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloud.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    public User findByUsername(String username);

    // SELECT * FROM usuarios where token = 'token';
    public User findByToken(String token);

}