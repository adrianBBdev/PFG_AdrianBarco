package com.abb.pfg.backend.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.abb.pfg.backend.entities.Role;
import com.abb.pfg.backend.entities.User;

import lombok.AllArgsConstructor;

/**
 * Custom implementation of UserDetails object of Spring Security
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
	
	private static final long serialVersionUID = 2950298448855725867L;
	private User user;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Role role = user.getRole();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
      
        authorities.add(new SimpleGrantedAuthority(role.getName()));
        
        return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
