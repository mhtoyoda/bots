package com.fiveware.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fiveware.model.TypeParameter;

@Repository
public interface TypeParameterRepository extends CrudRepository<TypeParameter, Long>{

	public TypeParameter findByName(String name);
}
