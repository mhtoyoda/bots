package com.fiveware.service.security;

import com.fiveware.model.user.IcaptorUser;
import com.fiveware.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.TreeSet;

/**
 * Created by valdisnei on 7/9/17.
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService{

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<IcaptorUser> clienteOptional = usuarioService.findByEmailAndAtivo(email);

        IcaptorUser usuario = clienteOptional.orElseThrow(() -> new UsernameNotFoundException("Usuario e/ou não encontrado!"));

        return new User(usuario.getEmail(),usuario.getPassword(),new TreeSet<>());
    }
}
