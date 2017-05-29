package com.fiveware;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by valdisnei on 29/05/17.
 */
@RestController
public interface ApiBot<T,R> {

    @GetMapping("/callBot/{parameter}")
    ResponseEntity<R> callBot(@PathVariable T parameter);
}
