package com.fiveware.service;

import com.fiveware.model.entities.Bot;
import org.springframework.stereotype.Service;

import com.fiveware.exception.ExceptionBot;
import com.fiveware.model.OutTextRecord;

import java.util.Optional;

/**
 * Created by valdisnei on 5/31/17.
 */
@Service
public interface ServiceBot {
    <T> OutTextRecord callBot(String nameBot, T parameter) throws ExceptionBot;
    <T> OutTextRecord callBot(String nameBot, String endpoint, T parameter) throws ExceptionBot;

    Optional<Bot> findByNameBot(String nameBot);

    Bot save(Bot bot);
}
