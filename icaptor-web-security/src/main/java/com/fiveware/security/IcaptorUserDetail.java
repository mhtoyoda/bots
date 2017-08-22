package com.fiveware.security;

import com.fiveware.model.user.IcaptorUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class IcaptorUserDetail implements UserDetails {

	private static final long serialVersionUID = -6895765035666474809L;

	private IcaptorUser user;

	public IcaptorUserDetail(IcaptorUser icaptorUser) {
		this.user = icaptorUser;
		getAuthorities();

	}

	public Long getUserId() {
		return user.getId();
	}

	public String getDisplayName() {
		return user.getName();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	public String getProfileImagePath() {
		return user.getProfileImagePath();
	}

	@Override
	public boolean isEnabled() {
		return user.getActive();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getPermissoes(this.user);
	}

	@Override
	public boolean isAccountNonExpired() {
		return user.getActive();
	}

	@Override
	public boolean isAccountNonLocked() {
		return user.getActive();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return user.getActive();
	}


	private Collection<? extends GrantedAuthority> getPermissoes(IcaptorUser usuario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		usuario.getGrupos().forEach(
				(g)->g.getPermissions().forEach(
						(p) -> authorities.add(new SimpleGrantedAuthority(p.getName().toUpperCase()))));
		return authorities;
	}

}
