package com.dinger.spring.model.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dinger.spring.model.dto.LoginResultDto;
import com.dinger.spring.model.dto.SignUpDto;
import com.dinger.spring.model.entity.Account;
import com.dinger.spring.model.repo.AccountRepo;


@Service
@Transactional
public class AccountService {

	@Autowired
	private AccountRepo repo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Transactional
	public Account signUp(SignUpDto dto) {
		var entity = dto.owner();
		entity.setPassword(encoder.encode(dto.getPassword()));		
		
		  if (repo.existsByUserNameOrEmail(entity.getUserName(),entity.getEmail())) {
		        throw new DuplicateKeyException("An error occurred while saving the owner information. Please choose a different userName or Email.");
		    }
		  try {
		        return repo.save(entity);
		    } catch (DataIntegrityViolationException ex) {
		        // Handle any other data integrity violation exception that might occur during save
		        throw new DuplicateKeyException("An error occurred while saving the owner information.", ex);
		    }
		 
	}
	
	@Transactional(readOnly = true)
	public Optional<LoginResultDto> findLoginUser(String userName) {
		return repo.findOneByUserName(userName).map(LoginResultDto::from);
	}

	

}
