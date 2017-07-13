package com.fiveware.service.bot;

import com.fiveware.model.entities.Bot;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by valdisnei on 13/07/17.
 */
public interface IServiceBot {
    @GetMapping("/name/{name}")
    Bot findByNameBot(@PathVariable String name);

    @PostMapping("/save")
    Bot save(@RequestBody Bot bot);
}
