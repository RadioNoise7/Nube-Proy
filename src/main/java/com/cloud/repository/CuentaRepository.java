package com.cloud.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cloud.model.Cuenta;

@Repository
public interface CuentaRepository extends CrudRepository<Cuenta, Integer> {

    List<Cuenta> findByNombre(String nombre);

    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods

    List<Cuenta> findByNombreContaining(String nombre); // LIKE %nombre%
}