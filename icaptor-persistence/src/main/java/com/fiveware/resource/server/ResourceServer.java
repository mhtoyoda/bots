package com.fiveware.resource.server;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiveware.model.Agent;
import com.fiveware.model.Server;
import com.fiveware.repository.ServerRepository;

/**
 * Created by valdisnei on 13/07/17.
 */
@EnableCaching
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

    @GetMapping("/{name}/name")
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