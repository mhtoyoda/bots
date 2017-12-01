package com.fiveware.service.agent;

import com.fiveware.model.Agent;
import com.fiveware.model.AgentParameter;
import com.fiveware.model.Bot;
import com.fiveware.model.Server;
import com.fiveware.repository.AgentParameterRepository;
import com.fiveware.repository.AgentRepository;
import com.fiveware.repository.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
public class ServiceAgentImpl {

    @Autowired
    private AgentParameterRepository agentParameterRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private ServerRepository serverRepository;

    public ServiceAgentImpl(AgentParameterRepository agentParameterRepository, AgentRepository agentRepository) {
        this.agentParameterRepository = agentParameterRepository;
        this.agentRepository= agentRepository;
    }

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

        if(null != agent.getServer()){
            Optional<Server> serverOptional = serverRepository.findByName(agent.getServer().getName());
            if(serverOptional.isPresent()){
                Server server = serverOptional.get();
                agent1.setServer(server);
            }
        }
        if(null != agent.getBots() && agent.getBots().size() > 0){
            agent1.setBots(agent.getBots());
        }
        agentRepository.save(agent1);

        return agent1;
    }

    public List<Agent> findAll(){
        return (List<Agent>) agentRepository.findAll();
    }


    public void remove(Agent agent){
        Agent agentdb = agentRepository.findByNameAgent(agent.getNameAgent());

        Optional<Agent> byNameAgent = (Optional<Agent>) Optional.ofNullable(agentdb);

        if(byNameAgent.isPresent()){
            agentRepository.delete(agentdb);
        }
    }

    public Agent findByNameAgent(@PathVariable String name){
        Agent byNameAgent = agentRepository.findByNameAgent(name);

        return byNameAgent;
    }

    public Agent findOne(Long id) {
        return agentRepository.findOne(id);
    }

    public Long count(){
        return agentRepository.count();
    }

    public List<Bot> findBotsByAgent(@PathVariable("nameAgent") String nameAgent){
        List<Bot> botsByAgent = agentRepository.findBynameAgent(nameAgent);
        return botsByAgent;
    }

    public List<Agent> findByNameBot(@PathVariable("nameBot") String nameBot){
        List<Agent> agents = agentRepository.findByBot(nameBot);
        return agents;
    }

    public AgentParameter findByParameterId(Long parameterId){
        AgentParameter agentParameter = agentParameterRepository.findByParameterId(parameterId);
        return agentParameter;
    }

    public AgentParameter findByAgentName(String nameAgent){
        AgentParameter agentParameter = agentParameterRepository.findByNameAgent(nameAgent);
        return agentParameter;
    }

    public AgentParameter save(AgentParameter agentParameter){
        agentParameter.setUseDate(LocalDateTime.now());
        return agentParameterRepository.save(agentParameter);
    }

    public void remove(AgentParameter agentParameter){
        agentParameter = agentParameterRepository.findOne(agentParameter.getId());

        Optional<AgentParameter> byAgentParameter = (Optional<AgentParameter>) Optional.ofNullable(agentParameter);

        if(byAgentParameter.isPresent()){
            agentParameterRepository.delete(agentParameter);
        }
    }
}
