package com.cloud.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cloud.model.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {

    List<Account> findByAccountname(String accountname);

    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods

    List<Account> findByAccountnameContaining(String accountname); // LIKE %nombre%
}