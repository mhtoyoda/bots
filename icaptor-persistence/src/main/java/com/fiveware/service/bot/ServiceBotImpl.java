package com.fiveware.service.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fiveware.model.entities.Bot;
import com.fiveware.repository.BotRepository;

import java.util.Optional;

/**
 * Created by valdisnei on 13/07/17.
 */
@RestController
@RequestMapping("/api/bot")
public class ServiceBotImpl implements IServiceBot {


    @Autowired
    private BotRepository botRepository;


    @Override
    @GetMapping("/name/{name}")
    public Bot findByNameBot(@PathVariable String name){
        Optional<Bot> byNameBot = botRepository.findByNameBot(name);

        return byNameBot.get();
    }

    @Override
    @PostMapping("/save")
    public Bot save(@RequestBody Bot bot){
        return botRepository.save(bot);
    }
}
