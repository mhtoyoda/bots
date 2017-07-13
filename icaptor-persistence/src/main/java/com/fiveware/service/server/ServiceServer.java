package com.fiveware.service.server;

import com.fiveware.model.entities.Agent;
import com.fiveware.model.entities.Server;
import com.fiveware.repository.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by valdisnei on 13/07/17.
 */
@RestController
@RequestMapping("/api/server")
public class ServiceServer implements IServiceServer {


    @Autowired
    private ServerRepository serverRepository;


    @Override
    @PostMapping("/save")
    public Server save(@RequestBody Server server) {
        return serverRepository.save(server);
    }


    @Override
    @GetMapping("/name/{name}")
    public Optional<Server> findByName(@PathVariable String name) {
        return serverRepository.findByName(name);
    }


    @Override
    @GetMapping("/agents/name/{name}")
    public List<Agent> getAllAgent(String name) {
        return serverRepository.getAllAgent(name);
    }

    @Override
    @GetMapping("/nameServer/{serverName}/nameBot/{nameBot}/endPoint/{endpoint}")
    public Optional<List<Agent>> getAllAgentsByBotName(String serverName, String nameBot, String endpoint){
        return serverRepository.getAllAgentsByBotName(serverName,nameBot,endpoint);

    }
}