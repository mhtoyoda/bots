package com.fiveware.resource.bot;

import com.fiveware.model.Bot;
import com.fiveware.repository.BotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by valdisnei on 13/07/17.
 */
@RestController
@RequestMapping("/api/bot")
public class ResourceBot {

    @Autowired
    private BotRepository botRepository;

    @PostMapping
    public Bot save(@RequestBody Bot bot){
        Optional<Bot> optional = botRepository.findByNameBot(bot.getNameBot());
        return optional.orElseGet(new Supplier<Bot>() {
            @Override
            public Bot get() {
                return botRepository.save(bot);
            }
        });
    }

    @GetMapping
    public ResponseEntity<Iterable<Bot>> findAll(){
        return ResponseEntity.ok(botRepository.findAll());

    }

    @GetMapping("/name/{name}")
    public Bot findByNameBot(@PathVariable("name") String name){
        Optional<Bot> byNameBot = botRepository.findByNameBot(name);
        return byNameBot.orElseThrow(() -> new IllegalArgumentException("Bot nao encontrado!"));
    }

}