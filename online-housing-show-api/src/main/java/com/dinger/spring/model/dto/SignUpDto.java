package com.dinger.spring.model.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.dinger.spring.model.entity.Owner;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SignUpDto {

	@NonNull
	private String userName;
	@NonNull
	private String password;
	@NonNull
	private String email;
	@NonNull
	private String name;
	
	public LoginDto signIn() {
		return new LoginDto(userName, password);
	}
	
	public Owner owner() {
		var owner = new Owner();
		owner.setUserName(userName);
		owner.setName(name);
		owner.setEmail(email);
		owner.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
		owner.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));
		return owner;
	}
}
