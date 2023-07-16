package com.dinger.spring.model.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

	private String userName;
	private String password;
	
	public UsernamePasswordAuthenticationToken authentication() {
		return new UsernamePasswordAuthenticationToken(userName, password);
	}}
