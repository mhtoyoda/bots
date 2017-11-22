package com.fiveware.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fiveware.model.BotFormatter;

@Repository
public interface BotFormatterRepository extends CrudRepository<BotFormatter, Long>{

	@Query("FROM BotFormatter b WHERE b.bot.nameBot = :nameBot")
	List<BotFormatter> findByBot(@Param("nameBot") String nameBot);

}
