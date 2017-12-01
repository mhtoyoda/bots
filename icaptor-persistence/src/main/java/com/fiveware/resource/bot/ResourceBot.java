package com.fiveware.resource.bot;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import com.fiveware.service.bot.ServiceBotImpl;
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
    private ServiceBotImpl serviceBot;

    @PostMapping
    public Bot save(@RequestBody Bot bot){
       return serviceBot.save(bot);
    }

    @GetMapping
    public ResponseEntity<Iterable<Bot>> findAll(){
        return ResponseEntity.ok(serviceBot.findAll());
    }

    @GetMapping("/name/{name}")
    public Bot findByNameBot(@PathVariable("name") String name){
        Optional<Bot> byNameBot = serviceBot.findByNameBot(name);
        return byNameBot.orElseThrow(() -> new IllegalArgumentException("Bot nao encontrado!"));
    }

    @GetMapping("/formatter/{nameBot}")
    public ResponseEntity<List<BotFormatter>> findBotFormatter(@PathVariable("nameBot") String nameBot){
        return ResponseEntity.ok(serviceBot.findByBot(nameBot));
    }
    
    @PostMapping("/formatter")
    public BotFormatter saveBotFormatter(@RequestBody BotFormatter botFormatter){    	      
    	return serviceBot.save(botFormatter);
    }
    
    @DeleteMapping("/formatter")
	public ResponseEntity<Void> deleteBotFormatter(@RequestBody List<BotFormatter> botFormatters) {
    	serviceBot.deleteBotFormatter(botFormatters);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}