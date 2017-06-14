package com.fiveware.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fiveware.model.Server;

@Repository
public interface ServerDAO extends CrudRepository<Server, Long>{

	Optional<Server> findByHost(String host);
}
