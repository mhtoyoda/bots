package com.fiveware.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fiveware.model.user.IcaptorUser;
import com.fiveware.service.ServiceUser;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private ServiceUser usuarioService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<IcaptorUser> clienteOptional = usuarioService.getUserByEmail(email);

		IcaptorUser icaptorUser = clienteOptional.orElseThrow(() -> new UsernameNotFoundException(createLoginFailMessage(email)));

		return new IcaptorUserDetail(icaptorUser);
	}

	protected String createLoginFailMessage(String email) {
		StringBuilder sb = new StringBuilder();
		sb.append("Usuário [");
		sb.append(email);
		sb.append("] não encontrado!");
		return sb.toString();
	}

}
