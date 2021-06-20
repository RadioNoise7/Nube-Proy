package com.cloud.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cloud.model.Provider;

@Repository
public interface ProviderRepository extends CrudRepository<Provider, Integer>{

    List<Provider> findByProvidername(String providername);

    List<Provider> findByProvidernameContaining(String providername);

    void deleteById(Integer id);
    
}
