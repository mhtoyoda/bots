package com.fiveware.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fiveware.model.user.IcaptorUser;

public class IcaptorUserDetail implements UserDetails {

	private static final long serialVersionUID = -6895765035666474809L;

	private IcaptorUser user;

	public IcaptorUserDetail(IcaptorUser icaptorUser) {
		this.user = icaptorUser;
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
		return null;
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

}
