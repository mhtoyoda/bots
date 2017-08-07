package com.fiveware.resource.agent;

import com.fiveware.model.Agent;
import com.fiveware.model.Bot;
import com.fiveware.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by valdisnei on 13/07/17.
 */
@RestController
@RequestMapping("/api/agent")
public class ResourceAgent {

	@Autowired
    private AgentRepository agentRepository;


	@Transactional(readOnly = false)
    @PostMapping
    public Agent save(@RequestBody Agent agent){
        Agent agentdb = agentRepository.findByNameAgent(agent.getNameAgent());

        Optional<Agent> byNameAgent = (Optional<Agent>) Optional.ofNullable(agentdb);

        byNameAgent.ifPresent(new Consumer<Agent>() {
            @Override
            public void accept(Agent _agent) {
                _agent.setPort(agent.getPort());
            }
        });


        Agent agent1 = byNameAgent
                .orElseGet(new Supplier<Agent>() {
                    @Override
                    public Agent get() {
                        return agent;
                    }
                });

        agentRepository.save(agent1);

        return agent1;
    }


    @GetMapping("/{name}/name")
    public Agent findByNameAgent(@PathVariable String name){
        Agent byNameAgent = agentRepository.findByNameAgent(name);

        return byNameAgent;
    }

    @GetMapping("/{id}")
    public Agent findOne(@PathVariable Long id){

        return agentRepository.findOne(id);
    }

    @GetMapping
    public ResponseEntity<Iterable<Agent>> findAll(){
        return ResponseEntity.ok(agentRepository.findAll());
    }

    @GetMapping("/count")
    public Long count(){
        return agentRepository.count();
    }

    @GetMapping("/bots/nameAgent/{nameAgent}")
    public List<Bot> findBotsByAgent(@PathVariable("nameAgent") String nameAgent){
        List<Bot> botsByAgent = agentRepository.findBynameAgent(nameAgent);
        return botsByAgent;
    }
}
