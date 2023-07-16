package com.dinger.spring.model.dto;

import com.dinger.spring.model.entity.Account;
import com.dinger.spring.model.entity.Account.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResultDto {
	
	private int id;
	private String userName;
	private String email;
	private Role role;
	

	public static LoginResultDto from(Account entity) {
		return new LoginResultDto(entity.getId(), entity.getUserName(), entity.getEmail(), entity.getRole());
	}
}
