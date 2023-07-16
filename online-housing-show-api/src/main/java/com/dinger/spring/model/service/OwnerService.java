package com.dinger.spring.model.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dinger.spring.model.entity.Owner;
import com.dinger.spring.model.repo.OwnerRepo;

@Service
public class OwnerService {
	
	@Autowired
	private OwnerRepo repo;

	@Transactional(readOnly = true)
	public Optional<Owner> getLoginUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = (String)auth.getPrincipal();
		return repo.findOneByuserName(userName);
	}
	
}
