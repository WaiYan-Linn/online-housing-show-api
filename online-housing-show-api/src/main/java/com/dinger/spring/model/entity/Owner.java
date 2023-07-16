package com.dinger.spring.model.entity;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Owner extends Account{

	private static final long serialVersionUID = 1L;

	public Owner() {
		super(Role.Owner);
	}
	
	
	
}
