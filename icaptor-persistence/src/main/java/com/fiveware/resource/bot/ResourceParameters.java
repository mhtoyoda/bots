package com.fiveware.resource.bot;

import com.fiveware.model.ParameterValueBot;
import com.fiveware.repository.ParameterBotValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by valdisnei on 13/07/17.
 */
@RestController
@RequestMapping("/api/parameters")
public class ResourceParameters {

    @Autowired
    private ParameterBotValueRepository parameterBotValueRepository;

    @GetMapping
    public ResponseEntity<Iterable<ParameterValueBot>> findAll(){
        return ResponseEntity.ok(parameterBotValueRepository.findAll());
    }

    @GetMapping("/nameBot/{nameBot}")
    public List<ParameterValueBot> findByParameterBotValues(@PathVariable("nameBot") String nameBot){
        return parameterBotValueRepository.findByParameterBotValues(nameBot);

    }
}
