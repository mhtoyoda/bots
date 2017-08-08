package com.fiveware.resource.server;

import com.fiveware.model.Agent;
import com.fiveware.model.Server;
import com.fiveware.repository.ServerRepository;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by valdisnei on 13/07/17.
 */
@RestController
@RequestMapping("/api/server")
public class ResourceServer {
    @Autowired
    private ServerRepository serverRepository;

    @PostMapping
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

    @GetMapping("/name/{name}")
    public Optional<Server> findByName(@PathVariable("name") String name) {
        return serverRepository.findByName(name);
    }


    @GetMapping("/agents/{name}/name")
    public Set<Agent> getAllAgent(@PathVariable("name") String name) {
        Optional<Server> byName = serverRepository.findByName(name);
        return byName.orElseGet(new Supplier<Server>() {
            @Override
            public Server get() {
                return new Server();
            }
        }).getAgents();
    }

    @GetMapping
    public ResponseEntity<Iterable<Server>> findAll() {
        return ResponseEntity.ok(serverRepository.findAll());
    }


    @GetMapping("/nameServer/{serverName}/nameBot/{nameBot}/endPoint/{endpoint}")
    public Optional<List<Agent>> getAllAgentsByBotName(@PathVariable("serverName") String serverName,
    		@PathVariable("nameBot") String nameBot, @PathVariable("endpoint")  String endpoint){
        return serverRepository.getAllAgentsByBotName(serverName,nameBot,endpoint);

    }
}