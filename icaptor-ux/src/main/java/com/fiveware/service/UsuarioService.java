package com.fiveware.service;

import java.util.Optional;

/**
 * Created by valdisnei on 12/07/17.
 */
public interface UsuarioService {
    Optional<Object> findByEmailAndAtivo(String email, boolean ativoInativo);
}
