package com.dinger.spring.model.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class Housing {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	
	@NotBlank
	@Column(nullable = false)
    private String housingName;

	@NotBlank
    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private int numberOfFloors;

    @Column(nullable = false)
    private int numberOfMasterRoom;

    @Column(nullable = false)
    private int numberOfSingleRoom;

    @Column(nullable = false)
    private int amount;
    
    @ManyToOne(optional = false)
    private Owner owner;
    
    private Timestamp createdDate;
	
	private Timestamp updatedDate;
	
}
