package com.dinger.spring.model.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.dinger.spring.model.dto.HousingDto;
import com.dinger.spring.model.entity.Housing;
import com.dinger.spring.model.entity.Owner;
import com.dinger.spring.model.repo.HousingRepo;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

@Service
public class HousingService {
	
	@Autowired
	private HousingRepo housingRepo;
	
	@Autowired
	private OwnerService service;

	@Transactional
	public Housing create(HousingDto dto) {
		var housing = new Housing();
		setData(housing,dto);
		return housingRepo.save(housing);
	}

	@Transactional
	public Housing update(int id, HousingDto dto) {
		var housing = housingRepo.findById(id).get();
		setData(housing,dto);
		return housing;
	}
	
	@Transactional(readOnly = true)
	public Page<Housing> publicSearch(String housingName, String address, Integer numberOfFloors, Integer numberOfMasterRoom,
			Integer numberOfSingleRoom, Integer amount, Timestamp createdDate, Pageable pageable) {
		
		List<Specification<Housing>> list=  search(housingName, address, numberOfFloors, numberOfMasterRoom, numberOfSingleRoom, amount, createdDate, pageable);
		Specification<Housing> specification = list.stream().reduce(Specification::and).orElse(null);

		
		return housingRepo.findAll(specification,pageable);
	}
	
	@Transactional(readOnly = true)
	public Page<Housing> searchByOwner(String housingName,  String address, Integer numberOfFloors, Integer numberOfMasterRoom,
			Integer numberOfSingleRoom, Integer amount, Timestamp createdDate, Pageable pageable) {
		
		List<Specification<Housing>> list=  search(housingName, address,numberOfFloors, numberOfMasterRoom, numberOfSingleRoom, amount, createdDate, pageable);
		
		list.add((root,query,cb) ->{
			 	Join<Housing, Owner> ownerJoin = root.join("owner", JoinType.INNER);
		        
			return cb.equal(ownerJoin.get("userName"), getUserName());
		});
		
		Specification<Housing> specification = list.stream().reduce(Specification::and).orElse(null);
		return housingRepo.findAll(specification,pageable);

	}
	
	private String getUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName =(String) auth.getPrincipal();
		return userName;
	}
	
	public List<Specification<Housing>> search(String housingName,  String address, Integer numberOfFloors, Integer numberOfMasterRoom,
			Integer numberOfSingleRoom, Integer amount, Timestamp createdDate, Pageable pageable){
		
		List<Specification<Housing>> list = new ArrayList<>();

	    if (StringUtils.hasLength(housingName)) {
	        list.add((root, query, cb) -> cb.like(cb.lower(root.get("housingName")), "%" + housingName.toLowerCase() + "%"));
	    }
	    
	    if (StringUtils.hasLength(address)) {
	        list.add((root, query, cb) -> cb.like(cb.lower(root.get("address")), "%" + address.toLowerCase() + "%"));
	    }

	    if (null != numberOfFloors && numberOfFloors > 0) {
	        list.add((root, query, cb) -> cb.equal(root.get("numberOfFloors"), numberOfFloors));
	    }

	    if (null != numberOfMasterRoom && numberOfMasterRoom > 0) {
	        list.add((root, query, cb) -> cb.equal(root.get("numberOfMasterRoom"), numberOfMasterRoom));
	    }

	    if (null != numberOfSingleRoom && numberOfSingleRoom > 0) {
	        list.add((root, query, cb) -> cb.equal(root.get("numberOfSingleRoom"), numberOfSingleRoom));
	    }

	    if (null != amount && amount > 0) {
	        list.add((root, query, cb) -> cb.lessThanOrEqualTo(root.get("amount"), amount));
	    }

	    if (createdDate != null) {
	        list.add((root, query, cb) -> {
	            LocalDateTime startOfDay = createdDate.toLocalDateTime().with(LocalTime.MIN);
	            LocalDateTime endOfDay = createdDate.toLocalDateTime().with(LocalTime.MAX);

	            return cb.between(root.get("createdDate"), Timestamp.valueOf(startOfDay), Timestamp.valueOf(endOfDay));
	        });
	    }
	    return list;
	}
	

	private void setData(Housing housing, HousingDto dto) {
		housing.setHousingName(dto.getHousingName());
		housing.setAddress(dto.getAddress());
		housing.setNumberOfFloors(dto.getNumberOfFloors());
		housing.setNumberOfMasterRoom(dto.getNumberOfMasterRoom());
		housing.setNumberOfSingleRoom(dto.getNumberOfSingleRoom());
		housing.setAmount(dto.getAmount());
		housing.setOwner(service.getLoginUser().get());
		
		if(housing.getId() == 0) {
			housing.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
			housing.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));

		}
		else {
			housing.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));
		}
	}

	    
}
