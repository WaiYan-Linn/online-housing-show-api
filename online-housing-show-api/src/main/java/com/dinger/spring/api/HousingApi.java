package com.dinger.spring.api;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dinger.spring.model.dto.HousingDto;
import com.dinger.spring.model.entity.Housing;
import com.dinger.spring.model.service.HousingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("housings")
public class HousingApi {
	
	@Autowired
	private HousingService service;

	@PostMapping
	public Housing create(@Valid @RequestBody HousingDto dto) {
		return service.create(dto);
	}
	
	@PutMapping("{id}")
	public Housing update(@PathVariable int id, @RequestBody HousingDto dto) {
		return service.update(id,dto);
	}
	
	 @GetMapping("owner")
	 public Page<Housing> searchHousing(	
	            @RequestParam(name = "housingName", required = false) String housingName,
	            @RequestParam(name = "address", required = false) String address,
	            @RequestParam(name = "numberOfFloors", required = false) Integer numberOfFloors,
	            @RequestParam(name = "numberOfMasterRoom", required = false) Integer numberOfMasterRoom,
	            @RequestParam(name = "numberOfSingleRoom", required = false) Integer numberOfSingleRoom,
	            @RequestParam(name = "amount", required = false) Integer amount,
	            @RequestParam(name = "createdDate", required = false) Timestamp createdDate,
	            @RequestParam(name = "page", defaultValue = "0") int page,
	            @RequestParam(name = "pageSize", defaultValue = "2") int pageSize
			 	) {

		 Pageable pageable = PageRequest.of(page, pageSize);
		 return service.searchByOwner(housingName, address, numberOfFloors, numberOfMasterRoom, numberOfSingleRoom, amount, createdDate, pageable);
	}
	
	
}
