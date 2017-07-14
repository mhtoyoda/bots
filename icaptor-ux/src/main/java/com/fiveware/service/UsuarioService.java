package com.fiveware.service;


import com.fiveware.model.Usuario;

import java.util.Optional;

/**
 * Created by valdisnei on 12/07/17.
 */
public interface UsuarioService {
    Optional<Usuario> findByEmailAndAtivo(String email, boolean ativoInativo);
}
