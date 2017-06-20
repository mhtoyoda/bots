package com.fiveware.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fiveware.model.Bot;

@Repository
public interface BotDAO extends CrudRepository<Bot, Long>{

	Optional<Bot> findByNameBot(String nameBot);
}
