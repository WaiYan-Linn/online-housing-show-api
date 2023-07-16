package com.dinger.spring.model.repo;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import com.dinger.spring.model.entity.Housing;

public interface HousingRepo extends JpaRepositoryImplementation<Housing, Integer> {

}
