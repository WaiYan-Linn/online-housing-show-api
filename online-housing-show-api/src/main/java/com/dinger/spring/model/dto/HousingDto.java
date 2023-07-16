package com.dinger.spring.model.dto;

import lombok.Data;

@Data
public class HousingDto {

	private String housingName;
	private String address;
	private int numberOfFloors;
	private int numberOfMasterRoom;
	private int numberOfSingleRoom;
	private int amount;
}
