package com.fiveware.service;

import com.fiveware.model.Usuario;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by valdisnei on 12/07/17.
 */
@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Override
    public Optional<Usuario> findByEmailAndAtivo(String email, boolean ativoInativo) {

        String pattern = "http://%s:%d/api/usuario/%s/%s";
        String localhost = String.format(pattern, "localhost", 8085, email, ativoInativo);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Usuario> result = restTemplate.exchange(localhost, HttpMethod.GET, null, Usuario.class);

        return Optional.of(result.getBody());
    }

}
