package com.dinger.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.dinger.spring.model.repo.AccountRepo;

@Component
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private AccountRepo repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repo.findOneByUserName(username).map( a-> User.withUsername(username)
				.password(a.getPassword())
				.authorities(a.getRole().name())
				.disabled(false)
				.build()).orElseThrow(() -> new UsernameNotFoundException(username));
	}

}
