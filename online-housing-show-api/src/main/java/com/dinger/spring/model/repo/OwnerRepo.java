package com.dinger.spring.model.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;

import com.dinger.spring.model.entity.Owner;

public interface OwnerRepo extends JpaRepositoryImplementation<Owner, Integer> {

    @Query("SELECT o FROM Owner o JOIN Account a ON o.id = a.id WHERE a.userName = :userName")
	Optional<Owner> findOneByuserName(@Param("userName") String userName);

}
