package com.dinger.spring.model.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import com.dinger.spring.model.entity.Account;

public interface AccountRepo extends JpaRepositoryImplementation<Account, Integer> {

	Optional<Account> findOneByUserName(String userName);

	boolean existsByUserNameOrEmail(String userName,String email);

	

}
