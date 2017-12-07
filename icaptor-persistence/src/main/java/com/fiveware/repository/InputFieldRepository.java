package com.fiveware.repository;

import com.fiveware.model.InputField;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InputFieldRepository extends CrudRepository<InputField, Long>{


}
