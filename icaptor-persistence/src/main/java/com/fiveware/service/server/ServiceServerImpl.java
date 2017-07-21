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
//        Optional<Server> byName = serverRepository.findByName(server.getName());
//        if (byName.isPresent())
//            server.setId(byName.get().getId());

        Server save = serverRepository.save(server);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
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