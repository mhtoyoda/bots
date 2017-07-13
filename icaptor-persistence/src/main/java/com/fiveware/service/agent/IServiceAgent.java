package com.fiveware.service.agent;

import com.fiveware.model.entities.Agent;
import com.fiveware.model.entities.Bot;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created by valdisnei on 13/07/17.
 */
public interface IServiceAgent {
    @PostMapping("/save")
    Agent save(@RequestBody Agent agent);

    @PostMapping("/name/{name}")
    Agent findByNameAgent(String name);

    @GetMapping("/id/{id}")
    Agent findOne(@PathVariable Long id);

    @GetMapping("/count")
    Long count();

    @GetMapping("/bots/name/{nameAgent}")
    List<Bot> findBotsByAgent(String nameAgent);
}
