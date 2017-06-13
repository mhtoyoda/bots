package com.fiveware.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fiveware.model.Bot;

@Repository
public interface BotDAO extends CrudRepository<Bot, Long>{

}
