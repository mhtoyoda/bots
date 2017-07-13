package com.fiveware.service;

import com.fiveware.model.entities.Usuario;
import com.fiveware.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Created by valdisnei on 06/06/17.
 */
@RestController
@RequestMapping("/api")
public class UsuarioService {

    static Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping(value = "/usuario/{email}/{ativo}")
    public ResponseEntity<Usuario> upload(@PathVariable String email, @PathVariable boolean ativo,
                                           HttpServletRequest httpRequest){
        Optional<Usuario> byEmailAndAtivo = usuarioRepository.findByEmailAndAtivo(email, ativo);

        Usuario usuario = byEmailAndAtivo.orElseThrow(() -> new IllegalArgumentException("Usuario nao encontrado!"));

        return ResponseEntity.ok(usuario);

    }
}
