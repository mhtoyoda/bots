package com.fiveware.service.server;

import com.fiveware.model.Agent;
import com.fiveware.model.Server;
import com.fiveware.repository.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

@Service
public class ServiceServerImpl {

    @Autowired
    private final ServerRepository serverRepository;

    public ServiceServerImpl(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    public Server save(Server server) {
        Optional<Server> byName = findByName(server.getName());

        Server serverSave = byName.orElseGet(new Supplier<Server>() {
            @Override
            public Server get() {
                return serverRepository.save(server);
            }
        });

        return serverSave;

    }

    public Optional<Server> findByName(String name) {
        return serverRepository.findByName(name);
    }

    public Set<Agent> getAllAgent(String name) {
        Optional<Server> byName = findByName(name);
        return byName.orElseGet(new Supplier<Server>() {
            @Override
            public Server get() {
                return new Server();
            }
        }).getAgents();
    }

    public Iterable<Server> findAll() {
        return serverRepository.findAll();
    }

    public Optional<List<Agent>> getAllAgentsByBotName(String serverName, String nameBot, String endpoint) {
        return serverRepository.getAllAgentsByBotName(serverName,nameBot,endpoint);
    }
}
