package com.fiveware.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fiveware.model.user.IcaptorUser;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by valdisnei on 12/07/17.
 */
@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Optional<IcaptorUser> findByEmailAndAtivo(String email) {

        String pattern = "http://%s:%d/api/usuario/email";
        String localhost = String.format(pattern, "localhost", 8085);

        UsuarioRest usuario = new UsuarioRest(email);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<UsuarioRest> entity = new HttpEntity<UsuarioRest>(usuario);

        ResponseEntity<IcaptorUser> usuarioResponseEntity = restTemplate.postForEntity(localhost, entity, IcaptorUser.class);

        return Optional.of(usuarioResponseEntity.getBody());
    }

    class UsuarioRest{
        final String email;

        public UsuarioRest(String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }
    }
}
