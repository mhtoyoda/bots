package com.fiveware.service.user;

import com.fiveware.event.RecursoCriadoEvent;
import com.fiveware.model.user.IcaptorUser;
import com.fiveware.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
public class ServiceUserImpl {

    @Autowired
    private final ApplicationEventPublisher publisher;

    @Autowired
    private final UserRepository userRepository;

    public ServiceUserImpl(ApplicationEventPublisher publisher, UserRepository userRepository) {
        this.publisher = publisher;
        this.userRepository = userRepository;
    }

    public IcaptorUser create(IcaptorUser usuario, HttpServletResponse response) {
        IcaptorUser usuarioSaved = userRepository.save(usuario);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, usuarioSaved.getId()));

        return usuarioSaved;
    }

    public IcaptorUser getUsuario(IcaptorUser email) {
        Optional<IcaptorUser> byEmailAndAtivo = userRepository.findByEmailAndActive(email.getEmail(), true);
        byEmailAndAtivo.orElseThrow(() -> new EmptyResultDataAccessException(1));
        return byEmailAndAtivo.get();
    }

    public IcaptorUser get(Long id) {
        Optional<IcaptorUser> usuario = Optional.ofNullable(userRepository.findOne(id));
        return usuario.orElseThrow(() -> new IllegalArgumentException("Usuario nao encontrado!"));
    }

    public Iterable<IcaptorUser> list() {
        return userRepository.findAll();
    }

}
