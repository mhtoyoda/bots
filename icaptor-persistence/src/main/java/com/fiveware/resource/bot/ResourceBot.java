package com.fiveware.resource.bot;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiveware.model.Bot;
import com.fiveware.model.BotFormatter;
import com.fiveware.repository.BotFormatterRepository;
import com.fiveware.repository.BotRepository;

/**
 * Created by valdisnei on 13/07/17.
 */
@RestController
@RequestMapping("/api/bot")
public class ResourceBot {

    @Autowired
    private BotRepository botRepository;
    
    @Autowired
    private BotFormatterRepository botFormatterRepository;
    
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

    @GetMapping("/formatter/{nameBot}")
    public ResponseEntity<List<BotFormatter>> findBotFormatter(@PathVariable("nameBot") String nameBot){
        return ResponseEntity.ok(botFormatterRepository.findByBot(nameBot));
    }
    
    @PostMapping("/formatter")
    public BotFormatter saveBotFormatter(@RequestBody BotFormatter botFormatter){    	      
    	return botFormatterRepository.save(botFormatter);
    }
    
    @DeleteMapping("/formatter")
	public ResponseEntity<Void> deleteBotFormatter(@RequestBody List<BotFormatter> botFormatters) {
    	botFormatterRepository.delete(botFormatters);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}