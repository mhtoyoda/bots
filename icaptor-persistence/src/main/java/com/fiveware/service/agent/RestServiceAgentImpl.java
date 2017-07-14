package com.fiveware.service.agent;

import com.fiveware.model.Agent;
import com.fiveware.model.Bot;
import com.fiveware.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by valdisnei on 13/07/17.
 */
@RestController
@RequestMapping("/api/agent")
public class RestServiceAgentImpl {

	@Autowired
    private AgentRepository agentRepository;


	@Transactional(readOnly = false)
    @PostMapping("/save")
    public Agent save(@RequestBody Agent agent){
        Agent save = agentRepository.save(agent);

        Agent build = new Agent.BuilderAgent()
                .id(save.getId())
                .nameAgent(save.getNameAgent())
                .port(save.getPort())
                .ip(save.getIp())
                .server(save.getServer())
                .bots(save.getBots())
                .build();

        return build;
    }


    @GetMapping("/name/{name}")
    public Optional<Agent> findByNameAgent(@PathVariable String name){
        Optional<Agent> byNameAgent = agentRepository.findByNameAgent(name);

        return byNameAgent;
    }

    @GetMapping("/id/{id}")
    public Agent findOne(@PathVariable Long id){

        return agentRepository.findOne(id);
    }

    @GetMapping("/count")
    public Long count(){
        return agentRepository.count();
    }

    @GetMapping("/bots/nameAgent/{nameAgent}")
    public List<Bot> findBotsByAgent(String nameAgent){
        List<Bot> botsByAgent = agentRepository.findBotsByAgent(nameAgent);
        return botsByAgent;
    }
}
