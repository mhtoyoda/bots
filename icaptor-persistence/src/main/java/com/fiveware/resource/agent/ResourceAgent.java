package com.fiveware.resource.agent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.fiveware.service.agent.ServiceAgentImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiveware.model.Agent;
import com.fiveware.model.AgentParameter;
import com.fiveware.model.Bot;
import com.fiveware.repository.AgentParameterRepository;
import com.fiveware.repository.AgentRepository;

/**
 * Created by valdisnei on 13/07/17.
 */
@RestController
@RequestMapping("/api/agent")
public class ResourceAgent {

	@Autowired
	private ServiceAgentImpl serviceAgent;

	@Transactional(readOnly = false)
    @PostMapping
    public Agent save(@RequestBody Agent agent){
        return serviceAgent.save(agent);
    }
	
	@Transactional(readOnly = false)
    @DeleteMapping
    public void remove(@RequestBody Agent agent){
        serviceAgent.remove(agent);
    }

    @GetMapping("/{name}/name")
    public Agent findByNameAgent(@PathVariable String name){
        return serviceAgent.findByNameAgent(name);
    }

    @GetMapping("/{id}")
    public Agent findOne(@PathVariable Long id){

        return serviceAgent.findOne(id);
    }

    @GetMapping
    public ResponseEntity<Iterable<Agent>> findAll(){
        return ResponseEntity.ok(serviceAgent.findAll());
    }

    @GetMapping("/count")
    public Long count(){
        return serviceAgent.count();
    }

    @GetMapping("/bots/nameAgent/{nameAgent}")
    public List<Bot> findBotsByAgent(@PathVariable("nameAgent") String nameAgent){
        List<Bot> botsByAgent = serviceAgent.findBotsByAgent(nameAgent);
        return botsByAgent;
    }
    
    @GetMapping("/agents/nameBot/{nameBot}")
    public List<Agent> findByNameBot(@PathVariable("nameBot") String nameBot){
        List<Agent> agents = serviceAgent.findByNameBot(nameBot);
        return agents;
    }
    
    @GetMapping("/parameter/id/{parameterId}")
    public AgentParameter findByParameterId(@PathVariable("parameterId") Long parameterId){
        AgentParameter agentParameter = serviceAgent.findByParameterId(parameterId);
        return agentParameter;
    }
    
    @GetMapping("/parameter/nameAgent/{nameAgent}")
    public AgentParameter findByAgentName(@PathVariable("nameAgent") String nameAgent){
        AgentParameter agentParameter = serviceAgent.findByAgentName(nameAgent);
        return agentParameter;
    }
    
    @Transactional(readOnly = false)
    @PostMapping("/parameter")
    public AgentParameter save(@RequestBody AgentParameter agentParameter){
    	agentParameter.setUseDate(LocalDateTime.now());
    	return serviceAgent.save(agentParameter);
    }
    
    @Transactional(readOnly = false)
    @DeleteMapping("/parameter")
    public void remove(@RequestBody AgentParameter agentParameter){
        serviceAgent.remove(agentParameter);
    }
}
