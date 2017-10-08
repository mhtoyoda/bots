package com.fiveware.repository;

import com.fiveware.model.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public  class Parameters {

    @Autowired
    private EntityManager entityManager;


    @Transactional
    public Parameter save(Parameter parameter) {
        return entityManager.merge(parameter);
    }

    @Transactional
    public List<Parameter> save(List<Parameter> parameters) {
        parameters.forEach((parameter -> {

            entityManager.merge(parameter);

        }));
        return parameters;
    }
}
