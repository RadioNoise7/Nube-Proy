package com.cloud.repository;

import java.util.Optional;

import com.cloud.model.Role;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    public Role findByRolename(String rolename);

    public Optional<Role> findById(Integer id);
}
