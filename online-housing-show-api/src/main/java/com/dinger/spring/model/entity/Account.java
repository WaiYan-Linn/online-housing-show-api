package com.dinger.spring.model.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@RequiredArgsConstructor
public class Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	
	@NotBlank
    @Column(nullable = false,unique = true)
	private String userName;
	
	@NotBlank
    @Column(nullable = false)
	private String name;
	
	@NotBlank
    @Column(nullable = false,unique = true)
	private String email;
	
	@NotBlank
    @Column(nullable = false)
	private String password;
    
	
    @NonNull
	@Column(nullable = false)
    @Enumerated(EnumType.STRING)
	private Role role;
	
	private Timestamp createdDate;
	
	private Timestamp updatedDate;
	
	public enum Role{
		Owner
	}
}
