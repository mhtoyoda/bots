package com.fiveware.resource.bot;

import java.util.List;
import java.util.Optional;

import com.fiveware.model.InputField;
import com.fiveware.service.bot.ServiceBotImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fiveware.model.Bot;
import com.fiveware.model.BotFormatter;

/**
 * Created by valdisnei on 13/07/17.
 */
@RestController
@RequestMapping("/api/bot")
public class ResourceBot {

    @Autowired
    private ServiceBotImpl serviceBot;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
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

    @GetMapping("/{id}/input-fields")
    public ResponseEntity<Bot> listInputFields(@PathVariable("id") Long id){
        return ResponseEntity.ok(serviceBot.findOne(id));
    }

}