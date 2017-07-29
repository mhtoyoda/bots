package com.fiveware.service.user;


import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiveware.event.RecursoCriadoEvent;
import com.fiveware.model.Usuario;
import com.fiveware.repository.UsuarioRepository;

/**
 * Created by valdisnei on 06/06/17.
 */
@RestController
@RequestMapping("/api/usuario")
public class UsuarioService {

    static Logger logger = LoggerFactory.getLogger(UsuarioService.class);


    @Autowired
    private ApplicationEventPublisher publisher;


    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario,HttpServletResponse response){
        Usuario usuarioSaved = usuarioRepository.save(usuario);

        publisher.publishEvent(new RecursoCriadoEvent(this, response, usuarioSaved.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSaved);
    }


    @PostMapping(value = "/email")
    public ResponseEntity<?> getUsuario(@RequestBody Usuario email){
        Optional<Usuario> byEmailAndAtivo = usuarioRepository.findByEmailAndAtivo(email.getEmail(), true);
        byEmailAndAtivo.orElseThrow(() -> new EmptyResultDataAccessException(1));
        return ResponseEntity.ok(byEmailAndAtivo.get());
    }

    @GetMapping("/{id}")
    public Usuario get(@PathVariable Long id){

        Optional<Usuario> usuario = Optional.ofNullable(usuarioRepository.findOne(id));

        return usuario.orElseThrow(() -> new IllegalArgumentException("Usuario nao encontrado!"));


    }


}
