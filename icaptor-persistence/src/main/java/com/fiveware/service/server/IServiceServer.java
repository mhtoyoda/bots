package com.fiveware.service.server;

import com.fiveware.model.entities.Agent;
import com.fiveware.model.entities.Server;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

/**
 * Created by valdisnei on 13/07/17.
 */
public interface IServiceServer {
    @PostMapping("/save")
    Server save(@RequestBody Server server);

    @GetMapping("/name/{name}")
    Optional<Server> findByName(@PathVariable String name);

    @GetMapping("/agents/name/{name}")
    List<Agent> getAllAgent(String name);
}
