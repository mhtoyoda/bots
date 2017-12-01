package com.fiveware.resource.user;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import com.fiveware.service.user.ServiceUserImpl;
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
import com.fiveware.model.user.IcaptorUser;
import com.fiveware.repository.user.UserRepository;

/**
 * Created by valdisnei on 06/06/17.
 */
@RestController
@RequestMapping("/api/usuario")
public class UserResource {
	static Logger logger = LoggerFactory.getLogger(UserResource.class);


	@Autowired
	private ServiceUserImpl serviceUser;

	@PostMapping
	public ResponseEntity<IcaptorUser> create(@RequestBody IcaptorUser usuario, HttpServletResponse response) {

		IcaptorUser usuarioSaved = serviceUser.create(usuario, response);

		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSaved);
	}

	@PostMapping(value = "/email")
	public ResponseEntity<?> getUsuario(@RequestBody IcaptorUser email) {
		IcaptorUser usuario = serviceUser.getUsuario(email);
		return ResponseEntity.ok(usuario);
	}

	@GetMapping("/{id}")
	public IcaptorUser get(@PathVariable Long id) {
		IcaptorUser icaptorUser = serviceUser.get(id);

		return icaptorUser;
	}

	@GetMapping
	public ResponseEntity<Iterable<IcaptorUser>> list() {
		Iterable<IcaptorUser> icaptorUsers = serviceUser.list();

		return ResponseEntity.ok(icaptorUsers);
	}

}
