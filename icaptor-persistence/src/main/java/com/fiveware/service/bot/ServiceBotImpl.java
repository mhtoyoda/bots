package com.fiveware.service.bot;

import com.fiveware.model.ParameterValueBot;
import com.fiveware.repository.ParameterBotValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fiveware.model.Bot;
import com.fiveware.repository.BotRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by valdisnei on 13/07/17.
 */
@RestController
@RequestMapping("/api/bot")
public class ServiceBotImpl {

    @Autowired
    private BotRepository botRepository;

    @Autowired
    private ParameterBotValueRepository parameterBotValueRepository;

    @GetMapping("/name/{name}")
    public Bot findByNameBot(@PathVariable String name){
        Optional<Bot> byNameBot = botRepository.findByNameBot(name);
        return byNameBot.get();
    }

    @PostMapping("/save")
    public Bot save(@RequestBody Bot bot){
        Optional<Bot> optional = botRepository.findByNameBot(bot.getNameBot());
        return optional.orElseGet(getSave(bot));
    }

    private Supplier<Bot> getSave(Bot bot) {
        Supplier<Bot> supplier = new Supplier<Bot>() {
            @Override
            public Bot get() {
                return botRepository.save(bot);
            }
        };
        return supplier;
    }


    @GetMapping("/parameters/nameBot/{nameBot}")
    public List<ParameterValueBot> findByParameterBotValues(String nameBot){
        return parameterBotValueRepository.findByParameterBotValues(nameBot);

    }
}
