package com.cloud.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cloud.model.Proveedor;

@Repository
public interface ProveedorRepository extends CrudRepository<Proveedor, Integer>{

    List<Proveedor> findByNombre(String nombre);

    List<Proveedor> findByNombreContaining(String nombre);

    void deleteById(Integer id);
    
}
