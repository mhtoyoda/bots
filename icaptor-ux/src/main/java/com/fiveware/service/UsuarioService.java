package com.fiveware.service;


import java.util.Optional;

import com.fiveware.model.user.IcaptorUser;

/**
 * Created by valdisnei on 12/07/17.
 */
public interface UsuarioService {
    Optional<IcaptorUser> findByEmailAndAtivo(String email);
}
