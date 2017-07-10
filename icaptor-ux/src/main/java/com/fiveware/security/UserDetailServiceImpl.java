package com.fiveware.security;

import com.fiveware.model.entities.Usuario;
import com.fiveware.repository.UsuarioRepository;
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
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> clienteOptional = usuarioRepository.findByEmailAndAtivo(email,true);



        Usuario usuario = clienteOptional.orElseThrow(() -> new UsernameNotFoundException("Usuario e/ou não encontrado!"));


        return new User(usuario.getEmail(),usuario.getSenha(),new TreeSet<>());
    }
}
