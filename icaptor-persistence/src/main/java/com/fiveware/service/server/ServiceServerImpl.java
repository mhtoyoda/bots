package com.fiveware.service.server;

import com.fiveware.model.Agent;
import com.fiveware.model.Server;
import com.fiveware.repository.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by valdisnei on 13/07/17.
 */
@RestController
@RequestMapping("/api/server")
public class ServiceServerImpl implements IServiceServer {


    @Autowired
    private ServerRepository serverRepository;


    @Override
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Server server) {
        Optional<Server> byName = serverRepository.findByName(server.getName());

        Server serverSave = byName.orElseGet(new Supplier<Server>() {
            @Override
            public Server get() {
                return serverRepository.save(server);
            }
        });

        return ResponseEntity.status(HttpStatus.CREATED).body(serverSave);
    }


    @Override
    @GetMapping("/name/{name}")
    public Optional<Server> findByName(@PathVariable("name") String name) {
        return serverRepository.findByName(name);
    }


    @Override
    @GetMapping("/agents/name/{name}")
    public List<Agent> getAllAgent(@PathVariable("name") String name) {
        return serverRepository.findByAgentsNameAgent(name);
    }

    @Override
    @GetMapping("/nameServer/{serverName}/nameBot/{nameBot}/endPoint/{endpoint}")
    public Optional<List<Agent>> getAllAgentsByBotName(@PathVariable("serverName") String serverName,
    		@PathVariable("nameBot") String nameBot, @PathVariable("endpoint")  String endpoint){
        return serverRepository.getAllAgentsByBotName(serverName,nameBot,endpoint);

    }
}