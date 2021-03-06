package com.fiveware.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fiveware.model.Bot;

@Repository
public interface BotRepository extends JpaRepository<Bot, Long> {

	Optional<Bot> findByNameBot(String nameBot);

}
