package com.fiveware.service.agent;

import com.fiveware.model.entities.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fiveware.model.entities.Agent;
import com.fiveware.repository.AgentRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by valdisnei on 13/07/17.
 */
@RestController
@RequestMapping("/api/agent")
public class ServiceAgent implements IServiceAgent{
    @Autowired
    private AgentRepository agentRepository;


    @Override
    @PostMapping("/save")
    public Agent save(@RequestBody Agent agent){
        return agentRepository.save(agent);
    }

    @Override
    @PostMapping("/name/{name}")
    public Agent findByNameAgent(@PathVariable String name){
        Optional<Agent> byNameAgent = agentRepository.findByNameAgent(name);

        return byNameAgent.get();
    }

    @Override
    @GetMapping("/id/{id}")
    public Agent findOne(@PathVariable Long id){

        return agentRepository.findOne(id);
    }

    @Override
    @GetMapping("/count")
    public Long count(){
        return agentRepository.count();
    }


    @Override
    @GetMapping("/bots/nameAgent/{nameAgent}")
    public List<Bot> findBotsByAgent(String nameAgent){
        return agentRepository.findBotsByAgent(nameAgent);
    }
}
